package com.example.project3softwaremeth;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * One of the types of student classes that extend NonResident
 */
public class TriState extends NonResident{
    private State state;
    private int nyDiscount=4000;
    private int ctDiscount=5000;
    public TriState(String name, Major major, int credits,State state){
        super(name,major,credits);
        this.state=state;
        
    }

    /**
     * calculates tuitionDue for students
     */
    @Override
    public void tuitionDue(){
      super.tuitionDue();
      if(this.getCredits()>=12){ // check if they are fulltime or not
      if(this.state.equals(State.NY)){
        this.setTuitionDue((Double.valueOf(this.getTuitionDue()-nyDiscount)));
      }
      if(this.state.equals(State.CT)){
        this.setTuitionDue((Double.valueOf(this.getTuitionDue()-ctDiscount)));
      }
        }
    }

    /**
     * toString Method that returns specifics to tristate student
     * @return
     */
    @Override
    public String toString(){
        return super.toString() +"(tri-state):"+state;
    }
}
