/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author maxim
 */
public class Complex extends Number
{
    public double imaginaryNumber=0;

    public Complex(double realNumber,double imaginaryNumber)
    {

        super(realNumber);

        this.imaginaryNumber=imaginaryNumber;

        // TODO Auto-generated constructor stub

    }


    public Complex()
    {

        super();

    }

    public double getImaginary()
    {

    return imaginaryNumber;

    }




    public void setImaginary(double imaginaryNumber)
    {

        this.imaginaryNumber = imaginaryNumber;

    }



    @Override

    public String toString()
    {

        // TODO Auto-generated method stub

        return this.getter()+"+"+this.getImaginary()+"i";

    }

    @Override

    public boolean equals(Object obj)
    {

        // TODO Auto-generated method stub

        if(obj instanceof Complex)

        {

            Complex cnum=(Complex)obj;

            return this.getter()==cnum.getter() && cnum.getImaginary()==this.getImaginary();

        }

        return false;

    }
}
