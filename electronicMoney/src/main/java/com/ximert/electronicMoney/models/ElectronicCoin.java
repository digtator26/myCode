package com.ximert.electronicMoney.models;

import java.math.BigInteger;
import java.util.Objects;

public class ElectronicCoin {

    private BigInteger a;
    private BigInteger b;
    private BigInteger c;
    private BigInteger k;
    private BigInteger v;
    private BigInteger Sa;
    private BigInteger Sb;

    public ElectronicCoin(BigInteger a, BigInteger b, BigInteger c, BigInteger k, BigInteger v, BigInteger sa, BigInteger sb) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.k = k;
        this.v = v;
        Sa = sa;
        Sb = sb;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
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

    public BigInteger getSb() {
        return Sb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectronicCoin coin = (ElectronicCoin) o;
        return Objects.equals(a, coin.a) && Objects.equals(b, coin.b) && Objects.equals(c, coin.c) && Objects.equals(k, coin.k) && Objects.equals(v, coin.v) && Objects.equals(Sa, coin.Sa) && Objects.equals(Sb, coin.Sb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, k, v, Sa, Sb);
    }

    @Override
    public String toString() {
        return "ElectronicCoin{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", k=" + k +
                ", v=" + v +
                ", Sa=" + Sa +
                ", Sb=" + Sb +
                '}';
    }
}
