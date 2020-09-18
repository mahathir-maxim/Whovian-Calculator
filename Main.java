/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.text.DecimalFormat;
//import java.util.Arrays;
//import java.util.Objects;
//import java.math.RoundingMode;

/**
 *
 * @author maxim
 */
public class Main
{
    private static final DecimalFormat  DF2 = new DecimalFormat("#0.00");

    public static void main(String[] args )throws IOException
    {

        Scanner input= new Scanner(System.in);
        System.out.println("Enter input file name: ");
        String fileName= input.nextLine();
        //System.out.println(fileName);
        File file=new File(fileName);
        Scanner infile= new Scanner (file);
        PrintWriter outfile= new PrintWriter("results.txt");

        if(!file.exists())
        {
            System.out.println("ERROR! INPUT FILE NOT OPEN");
        }
        else
        {
            //System.out.println("Input file opened.");
        }

        String firstOp;
        String operator;
        String secondOp;

        while (infile.hasNext())
        {
            String str= infile.nextLine();
            System.out.println(str);
            System.out.println("==================================================");

            String[] arr= correctExp(str);
            if (arr.length<3 || arr.length>3)
            {
                System.out.println("Error. Incorrect Expression!");
                continue;
            }

            firstOp=arr[0];
            operator=arr[1];
            secondOp=arr[2];
            System.out.println(firstOp);
            System.out.println(operator);
            System.out.println(secondOp);
            if (operator.length()!=1)
            {
                System.out.println("Error. Incorrect Operator");
                continue;
            }


           /*******************************************************/
            if ( /*firstOp!=null && secondOp!=null && operator!=null &&*/ isNumeric2(firstOp) && isNumeric2(secondOp))
            {
                //System.out.println("ABOUT TO CREATE OBJECTS");
                Object op1= new Object();
                Object op2= new Object();
                //Object op1=parse(firstOp, outfile);
                double [] array1=parse(firstOp,outfile);

                if (array1[0]==0 && array1[1]==0)
                    {
                        continue;
                    }
                if (array1[1]==0)
                {
                    op1= new Number (array1[0]);
                    System.out.println("Firstop Number");
                }
                else if (array1[1]!=0)
                {

                        op1= new Complex(array1[0],array1[1]);
                        System.out.println("FirstOp Complex");



                }

            //firstReal= array1[0];
            //firstImg=array1[1];

            /*if (firstImg==0)
            {
                Number operand1= new Number (firstReal);
            }
            else
            {
                Complex operand1=new Complex (firstReal, firstImg);
            }*/
                double [] array2=parse (secondOp, outfile);

                if (array2[0]==0 && array2[1]==0)
                    {
                        continue;
                    }

                if (array2[1]==0)
                {
                    op2= new Number (array2[0]);
                    System.out.println("SecondOp Number");
                }
                else if (array2[1]!=0)
                {

                        op2= new Complex(array2[0],array2[1]);
                        System.out.println("SecondOp Complex");

                }
            //secondReal=array2[0];
            //secondImg=array2[1];

           /*if (secondImg==0)
            {
                Number operand2= new Number (secondReal);
            }
            else
            {
                Complex operand2=new Complex (secondReal, secondImg);
            }*/

           // System.out.println(op1.toString());
            //System.out.println(op2.toString());


                String expression= firstOp+" "+operator+" "+secondOp+ "\t";

                performCalculation(op1,op2,operator,expression, outfile);
            }


        /*******************************************************/
           //firstOp=null;
           //secondOp=null;
           //operator=null;
        }
        infile.close();
        outfile.close();

    }

    public static double[] parse(String x, PrintWriter outfile)
    {
       double array[]={0,0};
       String realPart;
       String imgPart;
       int i=x.indexOf("i");
       if (i==-1)
       {
           System.out.println("Real number: "+x);
           array[0]=Double.parseDouble(x);
           //Number operand= new Number(array[0]);
           return array;
       }


       else
       {
           //System.out.println(i);

           int plusInd=x.indexOf("+");
           //System.out.println(plusInd);
           int minusInd=x.indexOf("-");
           //System.out.println(minusInd);

           if (minusInd==0)
           {
               String str=x.substring(1);
               int minInd= str.indexOf("-");
               int plsInd= str.indexOf("+");

               if (minInd!=-1 && minInd<i && plsInd==-1)
               {
                   realPart=str.substring(0,minInd);
                   System.out.println("minus index: "+minInd);
                   imgPart=str.substring(minInd,i-1);
                   System.out.println(realPart+" and "+ imgPart);
                   double temp=Double.parseDouble(realPart);
                   array[0]=(-1)*temp;
                   array[1]=Double.parseDouble(imgPart);
               }

               else if (plsInd!=-1 && plsInd<i && minInd==-1)
               {
                    realPart=str.substring(0,plsInd);
                    imgPart=str.substring(plsInd,i-1);
                    //System.out.println(realPart+" and "+ imgPart);
                    double temp=Double.parseDouble(realPart);
                    array[0]=(-1)*temp;
                    array[1]=Double.parseDouble(imgPart);
               }
               else if (plsInd==-1 && minInd==-1)
               {
                   imgPart=str.substring(0,i-1);
                   array[0]=0;
                   double temp =Double.parseDouble(imgPart);
                   array[1]=(-1)*temp;

               }
               else if ((plsInd!=-1 && i<plsInd && minInd==-1) || (minInd!=-1 && i<minInd && plsInd==-1))
               {
                   array[0]=0;
                   array[1]=0;
               }


           }


           else if (minusInd!=-1 && minusInd < i && plusInd==-1)
           {
               realPart=x.substring(0,minusInd);
               imgPart=x.substring(minusInd,i);
               //System.out.println(realPart+" and "+ imgPart);
               array[0]=Double.parseDouble(realPart);
               array[1]=Double.parseDouble(imgPart);
           }

           else if (plusInd!=-1 && plusInd < i && minusInd==-1)
           {
               realPart=x.substring(0,plusInd);
               imgPart=x.substring(plusInd,i);
               //System.out.println(realPart+" and "+ imgPart);
               array[0]=Double.parseDouble(realPart);
               array[1]=Double.parseDouble(imgPart);
           }

           else if (plusInd==-1 && minusInd==-1 && i!=0)
           {
               array[0]=0;
               imgPart=x.substring(0,i);
               array[1]=Double.parseDouble(imgPart);
           }

           else if ((plusInd!=-1 && minusInd==-1 && i<plusInd) || (minusInd!=-1 && plusInd==-1 && i<minusInd))
           {
               array[0]=0;
               array[1]=0;
           }


           /*else
           {
               outfile.println("enter the complex number in correct order");
           }
           */
           //Complex operand= new Complex(array[0], array [1]);
           System.out.println("real: "+array[0]);
           System.out.println("imaginary: "+array[1]);
           return array;

       }


    }

    public static void performCalculation(Object obj1, Object obj2, String operator, String expression, PrintWriter outfile)
    {
        switch(operator)
        {
            case "*": multiply(obj1,obj2,expression, outfile);
                      break;

            case "/": divide(obj1,obj2,expression, outfile);
                      break;

            case "+": add(obj1,obj2,expression, outfile);
                      break;

            case "-": subtract(obj1,obj2,expression, outfile );
                      break;

            //case "%": modulous(obj1,obj2,expression, outfile);
                      //break;

            case "<": lessThan(obj1,obj2,expression, outfile);
                      break;

            case ">": greaterThan(obj1,obj2,expression, outfile);
                      break;

            case "=": isEqualTo(obj1,obj2,expression, outfile);
                      break;
        }
    }







     public static void add (Object object1, Object object2, String expression, PrintWriter outfile)
    {
        if ( (object1 instanceof Complex) && (object2 instanceof Complex) )
        {
            Complex obj2=(Complex)object2;
            Complex obj1=(Complex)object1;

            double real= obj1.realNumber+obj2.realNumber;
            double img = obj1.imaginaryNumber+ obj2.imaginaryNumber;
            if (img < 0 )
            {
                outfile.println(expression+DF2.format(real)+DF2.format(img)+"i");

            }
            else if (img >0 )
            {
                outfile.println(expression+DF2.format(real)+"+"+DF2.format(img)+"i");
            }
            else if ( img==0)
            {
                outfile.println(expression+DF2.format(real));
            }
        }
        else if ( !(object1 instanceof Complex) && !(object2 instanceof Complex) )
        {
            Number obj1= (Number) object1;
            Number obj2= (Number) object2;

            double value= obj1.realNumber+obj2.realNumber;
            outfile.println(expression+DF2.format(value));
        }
        else if ( (object1 instanceof Number) && (object2 instanceof Complex) )
        {
            Number obj1= (Number) object1;
            Complex obj2= (Complex) object2;

            double real=obj1.realNumber+obj2.realNumber;
            double img=obj2.imaginaryNumber;

            if (img < 0 )
            {
                outfile.println(expression+DF2.format(real)+DF2.format(img)+"i");

            }
            else if (img >0 )
            {
                outfile.println(expression+DF2.format(real)+"+"+DF2.format(img)+"i");
            }
            else if ( img==0)
            {
                outfile.println(expression+DF2.format(real));
            }

        }
        else
        {
            Number obj2= (Number) object2;
            Complex obj1= (Complex) object1;

            double real= obj1.realNumber+obj2.realNumber;
            double img=obj1.imaginaryNumber;

            if (img < 0 )
            {
                outfile.println(expression+DF2.format(real)+DF2.format(img)+"i");

            }
            else if (img >0 )
            {
                outfile.println(expression+DF2.format(real)+"+"+DF2.format(img)+"i");
            }
            else if ( img==0)
            {
                outfile.println(expression+DF2.format(real));
            }

        }
    }



    public static void subtract (Object object1, Object object2, String expression, PrintWriter outfile)
    {
        if ( (object1 instanceof Complex) && (object2 instanceof Complex) )
        {
            Complex obj2=(Complex)object2;
            Complex obj1=(Complex)object1;

            double real= obj1.getter()-obj2.getter();
            double img = obj1.imaginaryNumber- obj2.imaginaryNumber;
            if (img < 0 )
            {
                outfile.println(expression+DF2.format(real)+DF2.format(img)+"i");

            }
            else if (img >0 )
            {
                outfile.println(expression+DF2.format(real)+"+"+DF2.format(img)+"i");
            }
            else if ( img==0)
            {
                outfile.println(expression+DF2.format(real));
            }
        }
        else if ( !(object1 instanceof Complex) && !(object2 instanceof Complex) )
        {
            Number obj1= (Number) object1;
            Number obj2= (Number) object2;

            double value= obj1.realNumber-obj2.realNumber;
            outfile.println(expression+DF2.format(value));
        }
        else if ( (object1 instanceof Complex) && (object2 instanceof Number) )
        {
            Complex obj1=(Complex)object1;
            Number obj2= (Number) object2;
           // Complex obj2= new Complex( objN2.realNumber,0);

            double real= obj1.realNumber-obj2.realNumber;
            double img = (-1)*obj1.imaginaryNumber;
            if (img < 0 )
            {
                outfile.println(expression+DF2.format(real)+DF2.format(img)+"i");

            }
            else if (img >0 )
            {
                outfile.println(expression+DF2.format(real)+"+"+DF2.format(img)+"i");
            }
            else if ( img==0)
            {
                outfile.println(expression+DF2.format(real)+"");
            }

        }
        else
        {
            Number obj1= (Number) object1;
            //Complex obj1= new Complex( objN1.realNumber,0);
            Complex obj2=(Complex)object2;

            double real= obj1.realNumber-obj2.realNumber;
            double img = (-1)*obj2.imaginaryNumber;
            if (img < 0 )
            {
                outfile.println(expression+DF2.format(real)+DF2.format(img)+"i");

            }
            else if (img >0 )
            {
                outfile.println(expression+DF2.format(real)+"+"+DF2.format(img)+"i");
            }
            else if ( img==0)
            {
                outfile.println(expression+DF2.format(real));
            }
        }

    }

    public static void multiply (Object object1, Object object2, String expression, PrintWriter outfile)
    {
        if ( (object1 instanceof Complex) && (object2 instanceof Complex) )
        {
            System.out.println("inside condition 1");
            Complex obj2=(Complex)object2;
            Complex obj1=(Complex)object1;

            double firstNoI= obj1.realNumber*obj2.realNumber;
            double withI=obj1.realNumber*obj2.imaginaryNumber+obj1.imaginaryNumber*obj2.realNumber;
            double minusForI= -1*obj1.imaginaryNumber*obj2.imaginaryNumber;
            double withoutI=firstNoI+minusForI;
            if (withI<0)
            {
                outfile.println(expression+DF2.format(withoutI)+DF2.format(withI)+"i");
            }
            else if (withI==0)
            {
                outfile.println(expression+DF2.format(withoutI));
            }
            else if (withI>0)
            {
                outfile.println(expression+DF2.format(withoutI)+"+"+DF2.format(withI)+"i");
            }

        }
        else if ( !(object1 instanceof Complex) && !(object2 instanceof Complex) )
        {
            System.out.println("inside condition 2");
            Number obj1= (Number) object1;
            Number obj2= (Number) object2;

            double value= obj1.realNumber*obj2.realNumber;
            outfile.println(expression+DF2.format(value));

        }
        else if ( (object1 instanceof Complex) && (object2 instanceof Number) )
        {
            System.out.println("inside condition 3");
            Complex obj1=(Complex)object1;
            Number  obj2= (Number) object2;


            double withoutI=obj1.realNumber*obj2.realNumber;
            double withI= obj1.imaginaryNumber*obj2.realNumber;
            if (withI<0)
            {
                outfile.println(expression+DF2.format(withoutI)+DF2.format(withI)+"i");
            }
            else if( withI>0)
            {
                outfile.println(expression+DF2.format(withoutI)+"+"+DF2.format(withI)+"i");
            }
            else if (withI==0)
            {
                outfile.println(expression+DF2.format(withoutI));
            }
        }
        else if ( (object1 instanceof Number) && (object2 instanceof Complex) )
        {
            System.out.println("inside condition 4");
            Number obj1= (Number) object1;
            //Complex obj1= new Complex( objN1.realNumber,0);
            Complex obj2=(Complex)object2;

            double withoutI= obj1.realNumber*obj2.realNumber;
            double withI=obj1.realNumber*obj2.imaginaryNumber;
            System.out.println("REAL: "+withoutI);
            System.out.println("IMAGINARY: "+withI);
            if (withI<0)
            {
                outfile.println(expression+DF2.format(withoutI)+DF2.format(withI)+"i");
            }
            else if(withI==0)
            {
                System.out.println("WITHI=0 CONDITION");
                outfile.println(expression+DF2.format(withoutI));
            }
            else if (withI>0)
            {
                outfile.println(expression+DF2.format(withoutI)+"+"+DF2.format(withI)+"i");
            }


        }

    }





    public static void divide (Object object1, Object object2, String expression, PrintWriter outfile)
    {
        if ( !(object1 instanceof Complex) && !(object2 instanceof Complex) )
        {
            Number obj2=(Number)object2;
            Number obj1=(Number)object1;
            if (obj2.realNumber!=0)
            {
                double value= obj1.realNumber/obj2.realNumber;
                outfile.println(expression+DF2.format(value));
            }
            else
            {
                outfile.println(expression+"Error: Cannot divide by 0");
            }

        }
        else if ((object1 instanceof Complex) && (object2 instanceof Complex))
        {
            Complex obj2=(Complex)object2;
            Complex obj1=(Complex)object1;

            Complex obj3= new Complex (obj2.realNumber, (-1)*obj2.imaginaryNumber);

            double denominator= obj2.realNumber*obj3.realNumber+(-1)*obj2.imaginaryNumber*obj3.imaginaryNumber+obj2.realNumber*obj3.imaginaryNumber+obj3.realNumber*obj2.imaginaryNumber;

            double firstNoI= obj1.realNumber*obj3.realNumber;
            double withI=obj1.realNumber*obj3.imaginaryNumber+obj1.imaginaryNumber*obj3.realNumber;
            double minusForI= -1*obj1.imaginaryNumber*obj3.imaginaryNumber;
            double withoutI=firstNoI+minusForI;

            if (withI>0)
            {
                outfile.println(expression+DF2.format(withoutI/denominator)+"+"+DF2.format(withI/denominator)+"i");
            }
            else if (withI==0)
            {
               outfile.println(expression+DF2.format(withoutI/denominator));
            }
            else if (withI<0)
            {
                outfile.println(expression+DF2.format(withoutI/denominator)+DF2.format(withI/denominator)+"i");
            }

        }
        else if ( (object1 instanceof Complex) && (object2 instanceof Number) )
        {
            Complex obj1=(Complex)object1;
            Number obj2= (Number) object2;

            double denominator= obj2.realNumber;
            double withI=obj1.imaginaryNumber/denominator;
            double withoutI=obj1.realNumber/denominator;
            if (withI>0)
            {
                outfile.println(expression+DF2.format(withoutI)+"+"+DF2.format(withI)+"i");
            }
            else if (withI==0)
            {
               outfile.println(expression+DF2.format(withoutI));
            }
            else if (withI<0)
            {
                outfile.println(expression+DF2.format(withoutI)+DF2.format(withI)+"i");
            }

        }
        else
        {
            Number obj1= (Number) object1;
            //Complex obj1= new Complex( objN1.realNumber,0);
            Complex obj2=(Complex)object2;

            Complex obj3= new Complex (obj2.realNumber, (-1)*obj2.imaginaryNumber);

            double denominator= obj2.realNumber*obj3.realNumber+(-1)*obj2.imaginaryNumber*obj3.imaginaryNumber+obj2.realNumber*obj3.imaginaryNumber+obj3.realNumber*obj2.imaginaryNumber;

            double withoutI= obj1.realNumber*obj3.realNumber;
            double withI=obj1.realNumber*obj3.imaginaryNumber;

            if (withI>0)
            {
                outfile.println(expression+DF2.format(withoutI/denominator)+"+"+DF2.format(withI/denominator)+"i");
            }
            else if (withI==0)
            {
               outfile.println(expression+DF2.format(withoutI/denominator));
            }
            else if (withI<0)
            {
                outfile.println(expression+DF2.format(withoutI/denominator)+DF2.format(withI/denominator)+"i");
            }
        }


    }

    //public static void modulous (Object obj1, Object obj2, String expression, PrintWriter outfile)
    //{

    //}

    public static void lessThan (Object object1, Object object2, String expression, PrintWriter outfile)
    {
        if ((object1 instanceof Complex) && (object2 instanceof Complex))
        {
            Complex obj2=(Complex)object2;
            Complex obj1=(Complex)object1;
            double mod1= (obj1.realNumber*obj1.realNumber)+(obj1.imaginaryNumber*obj1.imaginaryNumber);
            double mod2= (obj2.realNumber*obj2.realNumber)+(obj2.imaginaryNumber*obj2.imaginaryNumber);
            System.out.println("mod1: "+ mod1+ "mod2: "+mod2);
            if (mod1<mod2)
                outfile.println(expression+"True");
             else
                outfile.println(expression+"False");
        }
        else if ( !(object1 instanceof Complex) && !(object2 instanceof Complex) )
        {
            Number obj2=(Number)object2;
            Number obj1=(Number)object1;
            boolean value=obj1.realNumber < obj2.realNumber;
            if (value)
                outfile.println(expression+"True");
            else
                outfile.println(expression+"False");
        }
        else
            outfile.println(expression+"False");

    }

    public static void greaterThan (Object object1, Object object2, String expression, PrintWriter outfile)
    {
        if ((object1 instanceof Complex) && (object2 instanceof Complex))
        {
            Complex obj2=(Complex)object2;
            Complex obj1=(Complex)object1;
            double mod1= (obj1.realNumber*obj1.realNumber)+(obj1.imaginaryNumber*obj1.imaginaryNumber);
            double mod2= (obj2.realNumber*obj2.realNumber)+(obj2.imaginaryNumber*obj2.imaginaryNumber);
            System.out.println("mod1: "+ mod1+ "mod2: "+mod2);
            if (mod1>mod2)
                outfile.println(expression+"True");
             else
                outfile.println(expression+"False");
        }
        else if ( !(object1 instanceof Complex) && !(object2 instanceof Complex) )
        {
            Number obj2=(Number)object2;
            Number obj1=(Number)object1;
            boolean value=obj1.realNumber > obj2.realNumber;
            if (value)
                outfile.println(expression+"True");
            else
                outfile.println(expression+"False");
        }
        else
            outfile.println(expression+"False");


    }

    public static void isEqualTo (Object object1, Object object2, String expression, PrintWriter outfile)
    {
        if ((object1 instanceof Complex) && (object2 instanceof Complex))
        {
            Complex obj2=(Complex)object2;
            Complex obj1=(Complex)object1;
            //double mod1= (obj1.realNumber*obj1.realNumber)+(-1*obj1.imaginaryNumber*obj1.imaginaryNumber);
            //double mod2= (obj2.realNumber*obj2.realNumber)+(-1*obj2.imaginaryNumber*obj2.imaginaryNumber);
            boolean realEqual= obj1.realNumber== obj2.realNumber;
            boolean imgEqual=obj1.imaginaryNumber==obj2.imaginaryNumber;

            if (realEqual && imgEqual)
                outfile.println(expression+"True");
             else
                outfile.println(expression+"False");
        }
        else if ( !(object1 instanceof Complex) && !(object2 instanceof Complex) )
        {
            Number obj2=(Number)object2;
            Number obj1=(Number)object1;
            boolean value=obj1.realNumber == obj2.realNumber;
            if (value)
                outfile.println(expression+"True");
            else
                outfile.println(expression+"False");
        }
        else
            outfile.println(expression+"False");


    }


    public static boolean isNumeric(String str)
    {
        if (str.charAt(0)=='-')
        {
            str=str.substring(1);
        }
        String x= str.toLowerCase();
        if (x==null || x.length()==0)
        {
            return false;
        }
        else
        {
            for (int i=0; i<x.length(); i++)
                {
                    if (x.charAt(i)>=97 && x.charAt(i)!=105 && x.charAt(i)<=122)
                    {
                        return false;
                    }
                }

            if (!(x.contains("i")))
            {
                if (x.contains("."))
                {
                        int dot=x.indexOf(".");
                        String temp=x.substring(dot+1);
                        if (temp.contains("."))
                        {
                            return false;
                        }
                }

            }
            else
            {
                int minInd=x.indexOf("-");
                int plusInd=x.indexOf("+");

                if (minInd==-1 && plusInd==-1)
                {
                    if (x.contains("."))
                    {
                        int dot=x.indexOf(".");
                        String temp=x.substring(dot+1);
                        if (temp.contains("."))
                        {
                            return false;
                        }
                    }
                }

                else if (minInd!=-1)
                {
                        int num=x.indexOf("-");
                        String first, last;
                        first=x.substring(0,num);
                        last=x.substring(num);

                        if (first.contains("."))
                        {
                            int num1=first.indexOf(".");
                            String temp=first.substring(num1+1);
                            if (temp.contains("."))
                            {
                                return false;
                            }
                        }
                        if (last.contains("."))
                        {
                            int num1=last.indexOf(".");
                            String temp=last.substring(num1+1);
                            if (temp.contains("."))
                            {
                                return false;
                            }
                        }
                }

                else if (plusInd!=-1)
                {
                        int num=x.indexOf("+");
                        String first, last;
                        first=x.substring(0,num);
                        last=x.substring(num);

                        if (first.contains("."))
                        {
                            int num1=first.indexOf(".");
                            String temp=first.substring(num1+1);
                            if (temp.contains("."))
                            {
                                return false;
                            }
                        }
                        if (last.contains("."))
                        {
                            int num1=last.indexOf(".");
                            String temp=last.substring(num1+1);
                            if (temp.contains("."))
                            {
                                return false;
                            }
                        }
                }
            }

        }
        return true;
    }

    public static String[] correctExp(String str)
    {
        String[] arr=str.split("\\s+");
        return arr;


    }

    public static boolean isNumeric2(String str)
    {
        if (str.charAt(0)=='-')
        {
            str=str.substring(1);
        }
        String x= str.toLowerCase();
        if (x==null || x.length()==0)
        {
            System.out.println("NUMBER 1");
            return false;
        }
        //else
        //{
            for (int i=0; i<x.length(); i++)
                {
                    if (!((x.charAt(i)>=48 && x.charAt(i)<=57) || x.charAt(i)==105 || x.charAt(i)==43 || x.charAt(i)==45 ||x.charAt(i)==46))
                    {
                        System.out.println("NUMBER 2");
                        return false;
                    }
                }

            int indI, indPlus, indMinus, indDot;

            if (x.contains("i"))
            {
                int num=x.indexOf("i");
                String temp=x.substring(num+1);
                System.out.println(temp);
                if (temp.contains("i"))
                {
                    System.out.println("NUMBER 3");
                    return false;
                }
            }

            if (x.contains("+"))
            {
                int num=x.indexOf("+");
                String temp=x.substring(num+1);
                if (temp.contains("+"))
                {
                    System.out.println("NUMBER 4");
                    return false;
                }
            }

            if (x.contains("-"))
            {
                int num=x.indexOf("-");
                String temp=x.substring(num+1);
                if (temp.contains("-"))
                {
                    System.out.println("NUMBER 5");
                    return false;
                }
            }

            if (x.contains("-") && x.contains("+"))
            {
                System.out.println("NUMBER 6");
                return false;
            }


            if (!(x.contains("i")))
            {
                if (x.contains("+") || x.contains("-"))
                {
                    System.out.println("NUMBER 7");
                    return false;
                }

                if (x.contains("."))
                {
                    int num=x.indexOf(".");
                        if(!dotCheck(x,num))
                        {
                            System.out.println("NUMBER 8");
                            return false;
                        }
                }

            }
            else if (x.contains("i"))
            {
                indI=x.indexOf("i");
                if (!x.contains("+") && !x.contains("-"))
                {
                   if (x.contains(".") && x.indexOf(".")>indI)
                   {
                       System.out.println("NUMBER 9");
                       return false;
                   }

                   if (x.contains("."))
                   {
                       int num=x.indexOf(".");
                       if (!(dotCheck(x,num)))
                       {
                           System.out.println("NUMBER 10");
                           return false;
                       }
                   }


                }
                else if (x.contains("+") && x.indexOf("+")>indI)
                {
                    System.out.println("NUMBER 11");
                    return false;
                }
            else if (x.contains("+") && x.indexOf("+")<indI)
                {
                    int num=x.indexOf("+");
                    if (((x.charAt(num+1)>=48 && x.charAt(num+1)<=57) || x.charAt(num+1)==46) && (x.charAt(num-1)>=48 && x.charAt(num-1)<=57))
                    {
                        String first=x.substring(0,num);
                        String second=x.substring(num);
                        if (first.contains("."))
                        {
                            int i=first.indexOf(".");
                            String temp=first.substring(i+1);
                            if (!temp.isEmpty())
                            {
                                if ((first.charAt(i+1)>=48 && first.charAt(i+1)<=57) /*&& first.charAt(i-1)!=105*/)
                                {
                                //String temp=first.substring(i+1);
                                    if (temp.contains("."))
                                    {
                                        System.out.println("NUMBER 12");
                                        return false;
                                    }
                                }
                                else
                                {
                                    System.out.println("NUMBER 13");
                                    return false;
                                }
                            }
                            else
                                return false;
                        }
                        if (second.contains("."))
                        {
                            int i=second.indexOf(".");
                            String temp=second.substring(i+1);
                            if(!temp.isEmpty())
                            {
                                if ((second.charAt(i+1)>=48 && second.charAt(i+1)<=57) /*&& second.charAt(i-1)!=105*/)
                                {

                                    if (temp.contains("."))
                                    {
                                        System.out.println("NUMBER 14");
                                        return false;
                                    }
                                }
                                else
                                {
                                    System.out.println("NUMBER 15");
                                    return false;
                                }
                            }
                            else
                                return false;

                        }
                    }
                    else
                    {
                        System.out.println("NUMBER 16");
                        return false;
                    }
                }

            else if (x.contains("-") && x.indexOf("-")<indI)
                {
                    int num=x.indexOf("-");
                    if (((x.charAt(num+1)>=48 && x.charAt(num+1)<=57) || x.charAt(num+1)==46) && (x.charAt(num-1)>=48 && x.charAt(num-1)<=57))
                    {
                        String first=x.substring(0,num);
                        String second=x.substring(num);
                        if (first.contains("."))
                        {
                            int i=first.indexOf(".");
                            String temp=first.substring(i+1);
                            if (!temp.isEmpty())
                            {
                                if ((first.charAt(i+1)>=48 && first.charAt(i+1)<=57) && first.charAt(i-1)!=105)
                                {
                                    //String temp=first.substring(i+1);
                                    if (temp.contains("."))
                                    {
                                        System.out.println("NUMBER 17");
                                        return false;
                                    }
                                }
                                else
                                {
                                    System.out.println("NUMBER 18");
                                    return false;
                                }
                            }
                            else
                                return false;
                        }
                        if (second.contains("."))
                        {
                            int i=second.indexOf(".");
                            String temp=second.substring(i+1);
                            if (!temp.isEmpty())
                            {
                                if ((second.charAt(i+1)>=48 && second.charAt(i+1)<=57) && second.charAt(i-1)!=105)
                                {
                                    //String temp=second.substring(i+1);
                                    if (temp.contains("."))
                                    {
                                        System.out.println("NUMBER 19");
                                        return false;
                                    }
                                }
                                else
                                {
                                    System.out.println("NUMBER 20");
                                    return false;
                                }
                            }
                            else
                                return false;
                        }
                    }
                    else
                    {
                        System.out.println("NUMBER 21");
                        return false;
                    }
                }

            }

        return true;
    }

    public static boolean dotCheck( String str, int dot)
    {
        String temp=str.substring(dot+1);
        if (!temp.isEmpty())
        {
            if ((str.charAt(dot+1)>=48 && str.charAt(dot+1)<=57) /*&& str.charAt(dot-1)!=105*/ )
            {

                if (temp.contains("."))
                {
                    return false;
                }
                else
                    return true;
            }
            else
                return false;
        }
        else
            return false;
    }
}
