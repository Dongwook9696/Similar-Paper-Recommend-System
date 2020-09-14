package com.kimdongcheul.vec;

public class Vector_Similarity {
		
	public static double Cosine(DocVector d1, DocVector d2) {
		double cosinesimilarity = 0;
		cosinesimilarity = (d1.vector.dotProduct(d2.vector))
                / (d1.vector.getNorm() * d2.vector.getNorm());

		return cosinesimilarity;
	}


	public static double Euclidean(DocVector d1, DocVector d2) {
		double ED = 0;
		
		ED = d1.vector.getDistance(d2.vector);

		return ED;
	}

	public static double Theta(DocVector d1, DocVector d2) {
		double V = Cosine(d1, d2);
		double theta = Math.acos(V) + Math.toRadians(10);

		return theta;
	}

	public static double Triangle(DocVector d1, DocVector d2) {
		double theta = Theta(d1, d2);
		theta = Math.toRadians(theta);
		double TS = 0;
		TS = (d1.vector.getNorm() * d2.vector.getNorm() * Math.sin(theta)) / 2;


		return TS;

	}

	public static double Magnitude_Difference(DocVector d1, DocVector d2) {
		double MD = 0;
		MD = Math.abs(d1.vector.getNorm() - d2.vector.getNorm());

		return MD;
	}

	public static double Sector(DocVector d1, DocVector d2) {
		double SS = 0;
		SS = Math.PI * (Math.pow((Euclidean(d1, d2) + Magnitude_Difference(d1, d2)), 2)) * (Theta(d1, d2) / 360);

		return SS;
	}

	public static double TS_SS( DocVector d1, DocVector d2) {
		
		double TS_SS = 0;
		TS_SS = Triangle(d1, d2) * Sector(d1, d2);

		return TS_SS;
	}
}
