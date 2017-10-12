/*
 * Created on 08-mar-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ec.edu.puce.professorCheck.dto;

/**
 * @author felipe
 *
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 *         Entrada: Número de datos n, datos (x,y)
 */

public class Calculo5Dto {

	private int mNumPuntos;
	private double mPointX[];
	private double mPointY[];
	private double mCorrelacion;

	/**
	 * @return Returns the numPuntos.
	 */
	public int getNumPuntos() {
		return mNumPuntos;
	}

	/**
	 * @return Returns the Coeficiente de correlación.
	 */
	public double getCorrelacion() {
		int n = mNumPuntos;
		double cor = 0D;
		double num = 0D;
		double denom = 0D;
		double denom1 = 0D;
		double denom2 = 0D;

		num = n * getSumXY() - getSumX() * getSumY();
		denom1 = n * getSumXX() - Math.pow(getSumX(), 2);
		denom2 = n * getSumYY() - Math.pow(getSumY(), 2);
		denom = Math.sqrt(denom1 * denom2);
		cor = num / denom;

		mCorrelacion = cor;
		return mCorrelacion;
	}

	/**
	 * @param pNumPuntos
	 *            The numPuntos to set.
	 */
	public void setNumPuntos(int pNumPuntos) {
		mNumPuntos = pNumPuntos;
	}

	/**
	 * @return Returns the pointX.
	 */
	public double[] getPointX() {
		return mPointX;
	}

	/**
	 * @param pPointX
	 *            The pointX to set.
	 */
	public void setPointX(double[] pPointX) {
		mPointX = pPointX;
	}

	/**
	 * @return Returns the pointY.
	 */
	public double[] getPointY() {
		return mPointY;
	}

	/**
	 * @param pPointY
	 *            The pointY to set.
	 */
	public void setPointY(double[] pPointY) {
		mPointY = pPointY;
	}

	/**
	 * @return Suma de los datos x
	 */
	private double getSumX() {
		double sumX = 0D;
		for (int i = 0; i < mNumPuntos; i++) {
			sumX = sumX + mPointX[i];
		}
		return sumX;
	}

	/**
	 * @return Suma de los datos y
	 */
	private double getSumY() {
		double sumY = 0D;
		for (int i = 0; i < mNumPuntos; i++) {
			// double newY = mPointY[i];
			sumY = sumY + mPointY[i];
		}
		return sumY;
	}

	/**
	 * @return Suma de los cuadrados de los datos y
	 */
	private double getSumYY() {
		double sumYY = 0D;
		for (int i = 0; i < mNumPuntos; i++) {
			sumYY = sumYY + mPointY[i] * mPointY[i];
		}
		return sumYY;
	}

	/**
	 * @return Media de los datos x
	 */
	private double getMeanX() {
		double meanX = 0D;
		meanX = getSumX() / mNumPuntos;
		return meanX;
	}

	/**
	 * @return Media de los datos y
	 */
	private double getMeanY() {
		double meanY = 0D;
		meanY = getSumY() / mNumPuntos;
		return meanY;
	}

	/**
	 * @return Desviación estándar de los datos x
	 */
	private double getSDXN() {
		double sdxn = 0D;
		sdxn = Math.sqrt((mNumPuntos * getSumXX() - (Math.pow(getSumX(), 2))) / (Math.pow(mNumPuntos, 2)));
		return sdxn;
	}

	/**
	 * @return Desviación estándar de los datos y
	 */
	private double getSDYN() {
		double sdyn = 0D;
		sdyn = Math.sqrt((mNumPuntos * getSumYY() - (Math.pow(getSumY(), 2))) / (Math.pow(mNumPuntos, 2)));
		return sdyn;
	}

	/**
	 * @return Desviación estándar de la muestra de los datos x
	 */
	private double getSDX() {
		double sdx = 0D;
		sdx = Math.sqrt((mNumPuntos * getSumXX() - (Math.pow(getSumX(), 2))) / (mNumPuntos * (mNumPuntos - 1)));
		return sdx;
	}

	/**
	 * @return Desviación estándar de la muestra de los datos y
	 */
	private double getSDY() {
		double sdy = 0D;
		sdy = Math.sqrt((mNumPuntos * getSumYY() - (Math.pow(getSumY(), 2))) / (mNumPuntos * (mNumPuntos - 1)));
		return sdy;
	}

	/**
	 * @return Suma de los productos de los datos x por los datos y
	 */
	private double getSumXY() {
		double sumXY = 0D;
		for (int i = 0; i < mNumPuntos; i++) {
			// double newXY = mPointX[i]*mPointY[i];
			sumXY = sumXY + mPointX[i] * mPointY[i];
		}
		return sumXY;
	}

	/**
	 * @return Suma de los cuadrados de los datos x
	 */
	private double getSumXX() {
		double sumXX = 0D;
		for (int i = 0; i < mNumPuntos; i++) {
			sumXX = sumXX + mPointX[i] * mPointX[i];
		}
		return sumXX;
	}

	/**
	 * Coeficiente de correlación
	 */
	private void setCorrelacion() {
		int n = mNumPuntos;
		double cor = 0D;
		double num = 0D;
		double denom = 0D;
		double denom1 = 0D;
		double denom2 = 0D;

		num = n * getSumXY() - getSumX() * getSumY();
		denom1 = n * getSumXX() - Math.pow(getSumX(), 2);
		denom2 = n * getSumYY() - Math.pow(getSumY(), 2);
		denom = Math.sqrt(denom1 * denom2);
		cor = num / denom;

		mCorrelacion = cor;
	}

	public void calculate() {
		setCorrelacion();
	}

}
