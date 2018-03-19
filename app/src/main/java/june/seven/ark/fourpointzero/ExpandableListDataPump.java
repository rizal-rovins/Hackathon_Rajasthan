package june.seven.ark.fourpointzero;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JAYAN on 19-03-2018.
 */
public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> places_to_visit = new ArrayList<String>();


        List<String> pair_with_another = new ArrayList<String>();


        List<String> hire_tourist_support = new ArrayList<String>();
        hire_tourist_support.add("Locals + Translator");
        hire_tourist_support.add("Professional Guides");
        hire_tourist_support.add("Photographer");

        List<String> view_reviews=new ArrayList<String>();

        List<String> more_at_this_place=new ArrayList<String >();
        more_at_this_place.add("Food");
        more_at_this_place.add("Events");
        more_at_this_place.add("Famous places/markets");
        more_at_this_place.add("Things to do");

        List<String> health_and_security=new ArrayList<String>();
        List<String> souveniers=new ArrayList<String>();
        expandableListDetail.put("Places To Visit", places_to_visit);
        expandableListDetail.put("Pair with another fellow traveller", pair_with_another);
        expandableListDetail.put("Hire tourist support", hire_tourist_support);
        expandableListDetail.put("View stories/reviews about this place",view_reviews);
        expandableListDetail.put("More at this place",more_at_this_place);
        expandableListDetail.put("Health and security services",health_and_security);
        expandableListDetail.put("Find souveniers",souveniers);
        return expandableListDetail;
    }
}