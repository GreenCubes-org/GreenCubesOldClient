package net.lahwran.fevents;

public enum Order
{
  Earlist(0), 

  Early_IgnoreCancelled(1), 

  Early(2), 

  Default_IgnoreCancelled(3), 

  Default(4), 

  Late_IgnoreCancelled(5), 

  Late(6), 

  Latest_IgnoreCancelled(7), 

  Latest(8), 

  Monitor(9);

  private int index;

  private Order(int index) { this.index = index;
  }

  public int getIndex()
  {
    return this.index;
  }
}