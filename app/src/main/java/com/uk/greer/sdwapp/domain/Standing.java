package com.uk.greer.sdwapp.domain;

/**
 * Created by greepau on 13/05/2015.
 */
public class Standing implements DomainObject{

    private int totScrPts;
    private int totHcpPts;
    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private int scrpts;
    private int hcppts;
    private int entered;
    private int fin;
    private int dnf;
    private int dns;
    private Series series;


    public static Standing newInstance(int userId, String userName, String firstName, String lastName,
                                    int scrpts, int hcppts, int entered, int fin, int dnf, int dns, Series series, int totScrPts, int totHcpPts ){
        Standing standing = new Standing();
        standing.setUserName(userName);
        standing.setLastName(lastName);
        standing.setDnf(dnf);
        standing.setDns(dns);
        standing.setEntered(entered);
        standing.setFin(fin);
        standing.setFirstName(firstName);
        standing.setHcppts(hcppts);
        standing.setScrpts(scrpts);
        standing.setUserId(userId);
        standing.setSeries(series);
        standing.setTotHcpPts(totHcpPts);
        standing.setTotScrPts(totScrPts);
        return standing;
    }

    public int getTotScrPts() {
        return totScrPts;
    }

    public void setTotScrPts(int totScrPts) {
        this.totScrPts = totScrPts;
    }

    public int getTotHcpPts() {
        return totHcpPts;
    }

    public void setTotHcpPts(int totHcpPts) {
        this.totHcpPts = totHcpPts;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getScrpts() {
        return scrpts;
    }

    public void setScrpts(int scrpts) {
        this.scrpts = scrpts;
    }

    public int getHcppts() {
        return hcppts;
    }

    public void setHcppts(int hcppts) {
        this.hcppts = hcppts;
    }

    public int getEntered() {
        return entered;
    }

    public void setEntered(int entered) {
        this.entered = entered;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public int getDnf() {
        return dnf;
    }

    public void setDnf(int dnf) {
        this.dnf = dnf;
    }

    public int getDns() {
        return dns;
    }

    public void setDns(int dns) {
        this.dns = dns;
    }


    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public int getId() {
        return 0;
    }
}
