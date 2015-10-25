/**This program allows a customer to order pizza, cherry pie and Pi charms. It is set up with methods for 
 * ordering, paying, calculating subtotals, printing information and checking for errors.**/

import java.util.*;

public class Assignment1 {

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		//variables
		String help, pCard;
		String again="yes";
		int[] order;
		double[] sub;
		double total;

		//ask if there is a customer
		System.out.println("Is there a customer that needs to be waited on? (Yes/No)");
		help=input.next();

		//error check help
		help=ynErrorChk(help);

		while(again.equalsIgnoreCase("yes")){

			//help customer
			if(help.equalsIgnoreCase("Yes")){
				System.out.println("Do you have a Pie Card? (Yes/No)");
				pCard=input.next();

				//error check pCard
				pCard=ynErrorChk(pCard);

				//explain benefits if card holder, and display menu
				if(pCard.equalsIgnoreCase("Yes")){
					pCardBenefits();
					pCardMenu();
				}else{
					normalMenu();
				}

				//take order
				order=takeOrder();

				//tabulate and print subtotal
				if(pCard.equalsIgnoreCase("yes")){
					double woPc, wPc;

					//bill without discount
					sub=subtotal(order, "no", false);

					//EXTRA CREDIT: Show total overall savings with Pie Card
					System.out.println("\nYour bill without a Pie Card would have been:");
					if(sub[0]>0)
						System.out.println("Pizza:			$"+String.format("%.2f",sub[0]));
					if(sub[1]>0)
						System.out.println("Cherry Pie:		$"+String.format("%.2f",sub[1]));
					if(sub[2]>0)
						System.out.println("Pi Charm(s):		+$"+String.format("%.2f",sub[2]));
					System.out.println("		      ----------");
					System.out.println("Subtotal		 $"+String.format("%.2f",sub[3]));

					//add tax
					total=sub[3]*1.07;
					woPc=total;
					System.out.println("\nWith tax: Total	$"+String.format("%.2f",total));

					//bill with discount
					sub=subtotal(order, pCard, true);
					System.out.println("\nYour bill with a Pie Card is:");
					if(sub[0]>0)
						System.out.println("Pizza:			$"+String.format("%.2f",sub[0]));
					if(sub[1]>0)
						System.out.println("Cherry Pie:		$"+String.format("%.2f",sub[1]));
					if(sub[2]>0)
						System.out.println("Pi Charm(s):		+$"+String.format("%.2f",sub[2]));
					System.out.println("		      ----------");
					System.out.println("Subtotal		 $"+String.format("%.2f",sub[3]));

					//add tax
					total=sub[3]*1.07;
					wPc=total;
					System.out.println("\nWith tax: Total	$"+String.format("%.2f",total));
					System.out.println("\nYou saved: $"+String.format("%.2f",(woPc-wPc)));

				}else{
					sub=subtotal(order, pCard, true);
					if(sub[0]>0)
						System.out.println("Pizza:			$"+String.format("%.2f",sub[0]));
					if(sub[1]>0)
						System.out.println("Cherry Pie:		$"+String.format("%.2f",sub[1]));
					if(sub[2]>0)
						System.out.println("Pi Charm(s):		+$"+String.format("%.2f",sub[2]));
					System.out.println("		      ----------");
					System.out.println("Subtotal		 $"+String.format("%.2f",sub[3]));

					//add tax
					total=sub[3]*1.07;
					System.out.println("\nWith tax: Total	$"+String.format("%.2f",total));
				}
				//allow customer to pay
				pay(total);

				System.out.println("Would you like to submit another order? (Yes/No)");
				again=input.next();
				//check for error
				again=ynErrorChk(again);
			}
			//exits while - avoids infinite while loop if customer decides they do no want help.
			if(help.equals("no")){
			again="no";
			}
		}
	}


	//methods

	public static String ynErrorChk(String yn){		//checks if input was yes or no
		Scanner input = new Scanner(System.in);
		while(!yn.equalsIgnoreCase("Yes")&&!yn.equalsIgnoreCase("No")){
			System.out.println("Please enter Yes or No.");
			yn=input.next();
		}
		return yn;
	}

	public static void pCardBenefits(){				//explain pCard Benefits
		//explain benefits of Pie Card
		System.out.println("\nPie Card holders receive the following discounts on...");
		System.out.println("      Pizza:   Buy a pepperoni pizza for the price of a plain! ($10.00)");
		System.out.println("Cherry Pies:   Get $0.25 off per slice OR $2.00 off an entire pie!");
		System.out.println(" Pi  Charms:   Receive 10% off each charm you purchase!");
		System.out.println("    Overall:   If your order exceeds $100.00, all of the previous \n               discounts will apply in addition to 10% off your\n               entire order!");
	}

	public static void pCardMenu(){					//displays menu for pCard holders
		System.out.println("\n\nMenu:");
		System.out.println("Pizza		(1)Plain:$10.00\n     		(2)Pepperoni:$10.00");
		System.out.println("Cherry Pies	(3)$1.75 per slice OR $8.00 for an entire pie");
		System.out.println("Pi Charms	(4)$45.00 each [They are 14k gold]");
	}

	public static void normalMenu(){				//displays menu for non-pCard holders
		System.out.println("\nMenu:");
		System.out.println("Pizza		(1)Plain:$10.00\n     		(2)Pepperoni:$12.00");
		System.out.println("Cherry Pies	(3)$2.00 per slice OR $10.00 for an entire pie");
		System.out.println("Pi Charms	(4)$50.00 each [They are 14k gold]");
	}

	public static int[] takeOrder(){
		Scanner input = new Scanner(System.in);

		//set up order array
		int[] order = new int[5];
		for(int i=0; i<4; i++){
			order[i]=0;
		}

		//other vars
		String yn="";
		int index=0, item;
		int numCp, numPp, numWcher, numScher, numPc; //numWcher=whole pies, numScher=slices of pie		

		do{
			int cp=0, pp=0, cher=0, pc=0;
			System.out.println("\nPlease choose an item number:");		//customer enters item number
			System.out.println("(1) Cheese Pizza\n(2) Pepperoni Pizza\n(3) Cherry Pie (by the slice)\n(4) Pi Charms");
			item=input.nextInt();

			//error check
			while(item!=1&&item!=2&&item!=3&&item!=4){
				System.out.println("Please choose a valid item number (1, 2, 3 or 4).");
				item=input.nextInt();
			}

			if(item==1||item==2){
				//current order
				System.out.println("Here is your current pizza order:");
				cp=order[0];
				pp=order[1];

				System.out.println(cp+" cheese pizza(s) and "+pp+" pepperoni pizza(s).\n");

				if(item==1){		//order cheese
					//how many cheese pizza's
					System.out.println("How many Cheese Pizzas would you like?");
					numCp=input.nextInt();

					//add cheese pizza's to order
					order[0]+=numCp;

				}else if(item==2){		//order pepperoni

					//how many pepperoni pizza's
					System.out.println("How many Pepperoni Pizzas would you like?");
					numPp=input.nextInt();

					//add pepp pizza's to order
					order[1]+=numPp;
				}
			}else if(item==3){
				//current order
				System.out.println("Here is your current cherry pie order:");				
				numWcher=order[2];
				numScher=order[3];

				System.out.println(numWcher+" whole cherry pies and "+numScher+" slice(s) of cherry pie.");

				//add to order
				System.out.println("\nHow many slices of cherry pie would you like?");
				cher=input.nextInt();

				numWcher=cher/6;		//test
				numScher=cher%6;		//test

				order[2]+=numWcher;
				order[3]+=numScher;

				while(order[3]>=6){
					order[3]=order[3]-6;
					order[2]++;
				}

				System.out.println("You want:"+order[2]+" whole cherry pies and "+order[3]+" slice(s) of cherry pie.");		//test

			}else{
				//current order
				System.out.println("Here is your current Pi Charms order:");
				pc=order[4];
				System.out.println(pc+" 14K Pi charms");

				//add to order
				System.out.println("\nHow many Pi charms would you like?");
				numPc=input.nextInt();

				order[4]+=numPc;
			}

			//repeat?
			System.out.println("\nDo you want to add something else to your order(Yes/No)?");
			yn=input.next();
			yn=ynErrorChk(yn);
		}while(yn.equalsIgnoreCase("Yes"));

		//order array contains quantities of each possible item on the menu
		return order;
	}

	public static double[] subtotal(int[] order, String discount, boolean prntHelp){	//calculates customers subtotal
		Scanner input = new Scanner(System.in);
		double[] subtotal=new double[4];
		double sub=0, pizza, pie, pi;
		String add;

		if(discount.equalsIgnoreCase("yes")){
			pizza=10*(order[0]+order[1]);
			pie=(8*order[2])+(1.75*order[3]);
			pi=45*order[4];
			sub=pizza+pie+pi;
			if(sub>=100){
				sub=.9*sub;
				System.out.println("\nYou spent over $100.00 with your Pie Card, so you get %10 off your entire order!\n");
			}
		}else{
			pizza=(10*order[0])+(12*order[1]);
			pie=(10*order[2])+(2*order[3]);
			pi=50*order[4];
			sub=pizza+pie+pi;
		}

		subtotal[0]=pizza;
		subtotal[1]=pie;
		subtotal[2]=pi;
		subtotal[3]=sub;

		//subtotal array holds values for each item bought, and subtotal w/o tax
		return subtotal;
	}

	public static void pay(double total){	//allows customer to pay, finishing transaction
		Scanner input = new Scanner(System.in);
		double in, change;

		System.out.println("How much are you paying? (Please enter a number)");
		in=input.nextDouble();
		//underpay check
		while(in<total){
			System.out.println("Please pay the full bill. How much are you paying?");
			in=input.nextDouble();
		}

		//change
		if(in>total){
			change=in-total;
			System.out.println("You received $"+String.format("%.2f",change)+" in change");
		}

	}

}
