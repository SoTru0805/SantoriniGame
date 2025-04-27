package santorini.godcards;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodCardDeck implements DeckInit {
  private Map<String, Constructor<? extends GodCard>> map = new HashMap<>();

  public GodCardDeck(GodCard... godCardList) {
    for (GodCard godCard : godCardList) {
      try {
        Class<? extends GodCard> cls = godCard.getClass();
        Constructor<? extends GodCard> constructor = cls.getConstructor();
        map.put(godCard.getName(), constructor);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public GodCard removeGodCard(String godCardName) {
    try {
      Constructor<? extends GodCard> constructor = map.remove(godCardName); // remove after getting
      if (constructor != null) {
        return constructor.newInstance();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // Used for showing available God card names
  public String[] getAvailableGodCardNames() {
    return map.keySet().toArray(new String[0]);
  }

  // (Optional dummy shuffle method to match API style)
  public void shuffle() {
    // Nothing to shuffle inside map directly
    // We will shuffle keys at draw time
  }

  // New random draw method
  public GodCard draw() {
    if (map.isEmpty()) {
      return null;
    }
    List<String> keys = new ArrayList<>(map.keySet());
    Collections.shuffle(keys); // shuffle the list of names
    String randomKey = keys.get(0); // pick random key
    return removeGodCard(randomKey); // remove and return GodCard
  }
}
