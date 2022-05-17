package uta.cse3310;

import java.util.Arrays;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uta.cse3310.Dealer;
import java.util.ArrayList;
import java.util.List;
public class Player {
    int Id;
    String Name;
    public Card Cards[] = new Card[5];
    //for printing on html
    boolean Won = false;
    //private transient Dealer deal = new Dealer();
    //removed transient on money for testing purposes
    int money;
    //keeps track of players that have folded
    boolean folded = false;
    //starting amount of money
    transient int startingMoney;
    //Checks if player is ready
    transient boolean ready = false;
    //will keep track if player left
    boolean left = true;
    
    String LastMessageToPlayer;


    public Player(int id,Dealer deal) {
        Id = id;
        Name = "not set";
        //Cards = new Card[5];
        //deal.createHand(Cards);
        
        //System.out.println("Player ID " + Id + " has cards: " + Cards[0].value + " " + Cards[1].value + " "+ Cards[2].value + " "+ Cards[3].value + " "+ Cards[4].value + " ");
    }
 

    public void SetName(String N) {
        Name = N;
        LastMessageToPlayer="Welcome " + N + " to the game.";
    }
    //Draw functions
    public void playerDiscard(ArrayList<Integer> cardIndex, Dealer deal)
    {
        deal.discard(Cards, cardIndex);
    }

    public void playerDraw(ArrayList<Integer> cardIndex,Dealer deal)
    {
        deal.draw(Cards, cardIndex);
        System.out.println("Player ID" + Id + "has drawed");
    }
    //Betting functions
    /*public void bet(Dealer deal)
    {
        //amount will eventually take in a certain amount from events but for now just 10
        int amount = 20;
        deal.setBet(amount);
        money = 100 - deal.totalBet;
        System.out.println("Player ID" + Id + "has betted");
    }
    public void playerCheck()
    {
        //insert code here ;-;
        //actually don't think anything needs to go here.Maybe
        System.out.println("Player ID" + Id + "has checked");
    }*/
    //just realized check and call are kind of the same. and bet and raise are too
    public int playerCall(Dealer deal)
    {
        //this might be wrong
        //force players to fold if they cant match that amount for iteration 2 maybe
        //deal.totalBet-money;
        //if statement will be used to check if current player money can reach current betting amount. If not it will go as high as it can.
        int diff = money - (startingMoney -deal.totalBet);
        if(deal.totalBet>=startingMoney)
        {
            deal.pot += (money);
            money = 0;
        }
        else
        {
            money = startingMoney - deal.totalBet;
            deal.pot += diff;
        }
        System.out.println("Player ID" + Id + "has called");
        return 0;
    }

    public void playerRaise(int amount,Dealer deal)
    {
        //set to 20 in game until specific amounts of money are available
        //this might be wrong 
        int diff;
        if(deal.totalBet+ amount>=startingMoney)
        {
            diff = 0;
            if(money-amount<=0)
            {
                amount = money;
            }
            //diff is 0 because if you have a higher amount than current money in player than you'll just allocate amount to the pot.
            deal.setBet(amount);
            //prob smarter way to do all this but cant think rn
            amount = money;
            money = 0;
            deal.pot += (diff + amount);
            System.out.println("Player ID" + Id + "has raised");
        }
        else
        {
            diff = money - (startingMoney-deal.totalBet);
            deal.setBet(amount);
            money -= amount;
            money -= diff;
            deal.pot += (diff + amount);
            System.out.println("Player ID" + Id + "has raised");
        }
    }

    public void playerFold()
    {
        folded = true;
        System.out.println("Player ID" + Id + "has folded");
    }
    //for unittests
    public boolean getPlayerFoldStatus()
    {
        return folded;
    }
    public String getName()
    {
        return Name;
    }
    public int getNumberOfCards()
    {
        List<Card> cards = Arrays.asList(Cards);
        cards.toArray(Cards);
        int count = 0;
        for( int i = 0; i < cards.size(); i++)
        {
            if( Cards[i] != null)
                count++;
        }
        return count;
    }
    public String asJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}