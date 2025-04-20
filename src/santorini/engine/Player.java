//package santorini.engine;
//
//import santorini.elements.Worker;
//import santorini.elements.GodCard;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Player {
//  private String name;
//  private GodCard godCard;
//  private List<Worker> workers;
//
//  public Player(String name) {
//    this.name = name;
//    this.workers = new ArrayList<>();
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public void setGodCard(GodCard godCard) {
//    this.godCard = godCard;
//    godCard.setPlayer(this); // Link god card back to this player
//  }
//
//  public GodCard getGodCard() {
//    return godCard;
//  }
//
//  public void addWorker(Worker worker) {
//    workers.add(worker);
//  }
//
//  public List<Worker> getWorkers() {
//    return workers;
//  }
//}
