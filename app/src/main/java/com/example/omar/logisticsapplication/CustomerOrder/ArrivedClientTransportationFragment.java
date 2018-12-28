package com.example.omar.logisticsapplication.CustomerOrder;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.logisticsapplication.ApplicationActivity;
import com.example.omar.logisticsapplication.Config;
import com.example.omar.logisticsapplication.R;
import com.example.omar.logisticsapplication.classes.ArrivedTransportationInterface;
import com.example.omar.logisticsapplication.classes.Driver;
import com.example.omar.logisticsapplication.classes.Transportation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ArrivedClientTransportationFragment extends DialogFragment {

    TextView startTime;
    TextView endTime;
    TextView cph;
    TextView totalHours;
    TextView totalCost;
    Transportation t;
    ArrivedTransportationInterface mListener;
    Button done;

    public ArrivedClientTransportationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_arrived_transportation, container, false);
        startTime = view.findViewById(R.id.txt_startTime);
        endTime = view.findViewById(R.id.txt_endTime);
        cph = view.findViewById(R.id.txt_costPerHour);
        totalHours = view.findViewById(R.id.txt_totalHours);
        totalCost = view.findViewById(R.id.txt_totalCost);

        done = view.findViewById(R.id.btn_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrivedTransportationDB(t.getOrderNumber(),t.getDriver());
            }
        });

        t = (Transportation)getArguments().getSerializable("transportation");
        int costPerH = Integer.parseInt(t.getDriver().getCost());
        int h = getTotalHours(t.getStartTime(), getCurrentDate());
        int c = h*costPerH;
        Log.d("tst", ""+t.getDriver().getCost());

        startTime.setText(t.getStartTime());
        endTime.setText(getCurrentDate());
        cph.setText(costPerH+" LE");
        totalHours.setText(h+"");
        totalCost.setText(c+" LE");
        return view;
    }

    public void setListener(ArrivedTransportationInterface listener) {
        mListener = listener;
    }

    private void arrivedTransportationDB(final String orderNo, final Driver driver) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.TEST_ARRIVEDCLIENTSTRANSPOTATIONS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("tst", "Register Response: " + response.toString());
                //check for success first
                mListener.onArrive();

                dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<String, String>();
                params.put("order_number", orderNo);
                params.put("driver_name", driver.getName());
                params.put("driver_mobile", driver.getMobile());

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public int getTotalHours(String startTime, String endTime){

        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");

        try {
            Date date1 = format.parse(endTime);
            Date date2 = format.parse(startTime);
            long mills = date1.getTime() - date2.getTime();
            Log.d("tst", ""+mills);
            Log.v("Data1", ""+date1.getTime());
            Log.v("Data2", ""+date2.getTime());
            int hours = (int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;
            Log.d("tst", ""+hours+" , "+mins);

            return hours;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        String formattedDate = df.format(c);

        return formattedDate;

    }

}
