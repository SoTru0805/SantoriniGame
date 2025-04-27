package santorini.godcards;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class GodCardDeck implements DeckInit {
  private Map<String, Constructor<? extends GodCard>> map = new HashMap<>();

  public GodCardDeck(GodCard... GodCardList) {
    for (GodCard godCard : GodCardList) {
      try {
        Class<? extends GodCard> cls = godCard.getClass();
        Constructor<? extends GodCard> constructor;
        constructor = cls.getConstructor();
        map.put(godCard.getName(), constructor);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public GodCard removeGodCard(String godCardName) {
    try {
      Constructor<? extends GodCard> constructor = map.remove(godCardName); // <-- remove after getting
      if (constructor != null) {
        return constructor.newInstance();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public String[] getAvailableGodCardNames() {
    return map.keySet().toArray(new String[0]);
  }
}
