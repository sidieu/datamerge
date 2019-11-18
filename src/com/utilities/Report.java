/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilities;

/**
 *
 * @author smuyila
 */
public class Report {
    
     private String clientaddress;
     private String clientguid;
     private String requesttime;
     private String serviceguid;
     private String retriesrequest;
     private String packetsrequested;
     private String packetsserviced;
     private String maxholesize;

    public String getClientaddress() {
        return clientaddress;
    }

    public void setClientaddress(String clientaddress) {
        this.clientaddress = clientaddress;
    }

    public String getClientguid() {
        return clientguid;
    }

    public void setClientguid(String clientguid) {
        this.clientguid = clientguid;
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public String getServiceguid() {
        return serviceguid;
    }

    public void setServiceguid(String serviceguid) {
        this.serviceguid = serviceguid;
    }

    public String getRetriesrequest() {
        return retriesrequest;
    }

    public void setRetriesrequest(String retriesrequest) {
        this.retriesrequest = retriesrequest;
    }

    public String getPacketsrequested() {
        return packetsrequested;
    }

    public void setPacketsrequested(String packetsrequested) {
        this.packetsrequested = packetsrequested;
    }

    public String getPacketsserviced() {
        return packetsserviced;
    }

    public void setPacketsserviced(String packetsserviced) {
        this.packetsserviced = packetsserviced;
    }

    public String getMaxholesize() {
        return maxholesize;
    }

    public void setMaxholesize(String maxholesize) {
        this.maxholesize = maxholesize;
    }
    
}
