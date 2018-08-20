package com.coship.bean.table;

public class PmtStream {

    /**
     * stream_type : 8 bit
     */
    private int streamType;

    /**
     * ES_pid : 13 bit
     */
    private int esPid;

    /**
     * 
     * es_info_length : 12 bit
     */
    private int esInfoLength;


    public PmtStream(int streamType, int elementaryPid, int esInfoLength) {
        super();
        this.streamType = streamType;
        this.esPid = elementaryPid;
        this.esInfoLength = esInfoLength;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public int getesPid() {
        return esPid;
    }

    public void setEsPid(int esPid) {
        this.esPid = esPid;
    }

    public int getEsInfoLength() {
        return esInfoLength;
    }

    public void setEsInfoLength(int esInfoLength) {
        this.esInfoLength = esInfoLength;
    }
}
