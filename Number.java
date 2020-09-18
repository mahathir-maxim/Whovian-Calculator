/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author maxim
 */
public class Number
{
    public double realNumber=0;

    public Number(double realNumber)
    {

    this.realNumber= realNumber;

    }

    public Number()
    {

        //this.realNumber= realNumber;

    }



    public double getter()
    {

        return realNumber;

    }



    public void setter(double realNumnber)
    {

        this.realNumber = realNumnber;

    }



    @Override

    public String toString()
    {

        // TODO Auto-generated method stub

        return this.getter()+"";

    }

    @Override

    public boolean equals(Object obj)
    {

        // TODO Auto-generated method stub

        if(obj instanceof Number)

        {

            Number num=(Number)obj;

            return this.getter()==num.getter();

        }

        return false;

    }

}
