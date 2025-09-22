package edu.ccrm.domain;

public enum Grade {
    S(10), A(9), B(8), C(7), D(6), E(5), F(0);

    private final int points;
    Grade(int p) { this.points = p; }
    public int getPoints() { return points; }

    public static Grade fromPercentage(int percent) {
        if (percent >= 90) return S;
        if (percent >= 80) return A;
        if (percent >= 70) return B;
        if (percent >= 60) return C;
        if (percent >= 50) return D;
        if (percent >= 40) return E;
        return F;
    }
}
