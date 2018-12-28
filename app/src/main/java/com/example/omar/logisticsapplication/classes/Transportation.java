package com.example.omar.logisticsapplication.classes;

import java.io.Serializable;

public class Transportation implements Serializable {

    private Driver driver;
    private String orderFrom; // "Supplier"  or  "Client"
    private String orderNumber;
    private String startTime;
    private String endTime;
    private String totalPrice;

    public Transportation(Driver driver, String orderNumber) {
        this.driver = driver;
        this.orderNumber = orderNumber;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    /*public void getCurrentDate(){
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
        Date date1 = format.parse("08:00:12 pm");
        Date date2 = format.parse("05:30:12 pm");
        long mills = date1.getTime() - date2.getTime();
        Log.v("Data1", ""+date1.getTime());
        Log.v("Data2", ""+date2.getTime());
        int hours = (int) (mills/(1000 * 60 * 60));
        int mins = (int) (mills/(1000*60)) % 60;

        String diff = hours + ":" + mins; // updated value every1 second
        txtCurrentTime.setText(diff);
    }*/
}
