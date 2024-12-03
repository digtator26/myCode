package com.ximert.electronicMoney.models;

import java.math.BigInteger;
import java.util.Objects;

public class SpentElectronicCoin {

    private BigInteger a;
    private BigInteger c;
    private BigInteger k;
    private BigInteger v;
    private BigInteger Sa;
    private BigInteger x;
    private BigInteger r;

    public SpentElectronicCoin(BigInteger a, BigInteger c, BigInteger k, BigInteger v, BigInteger sa, BigInteger x, BigInteger r) {
        this.a = a;
        this.c = c;
        this.k = k;
        this.v = v;
        Sa = sa;
        this.x = x;
        this.r = r;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getC() {
        return c;
    }

    public BigInteger getK() {
        return k;
    }

    public BigInteger getV() {
        return v;
    }

    public BigInteger getSa() {
        return Sa;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getR() {
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpentElectronicCoin that = (SpentElectronicCoin) o;
        return Objects.equals(a, that.a) && Objects.equals(c, that.c) && Objects.equals(k, that.k) && Objects.equals(v, that.v) && Objects.equals(Sa, that.Sa) && Objects.equals(x, that.x) && Objects.equals(r, that.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, c, k, v, Sa, x, r);
    }

    @Override
    public String toString() {
        return "SpentElectronicCoin{" +
                "a=" + a +
                ", c=" + c +
                ", k=" + k +
                ", v=" + v +
                ", Sa=" + Sa +
                ", x=" + x +
                ", r=" + r +
                '}';
    }

}
