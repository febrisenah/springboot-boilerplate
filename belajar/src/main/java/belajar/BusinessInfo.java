package belajar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore null fields in JSON
public class BusinessInfo {
    public static class About {
        public String locationName = "string";
        public LocationCategory locationCategory = new LocationCategory();
        public String description = "string";
        public String openDate = "2025-01-23T08:30:05+00:00";
    }

    public static class LocationCategory {
        public String primary = "string";
        public List<String> additional = Arrays.asList("string");
    }

    public static class ContactInfo {
        public List<PhoneNumber> phoneNumber = Arrays.asList(new PhoneNumber());
        public String website = "string";
        public List<SocialProfile> socialProfile = Arrays.asList(new SocialProfile());
    }

    public static class PhoneNumber {
        public String country = "string";
        public String number = "string";
    }

    public static class SocialProfile {
        public String type = "string";
        public String url = "string";
    }

    public static class LocationArea {
        public boolean showAddress = true;
        public String country = "string";
        public String primaryAddress = "string";
        public String additionalAddress = "string";
        public String postalCode = "string";
        public String provinceId = "string";
        public String cityId = "string";
        public String coverageAreaId = "string";
        public double latitude = 0;
        public double longitude = 0;
        public List<ServiceArea> serviceAreaId = Arrays.asList(new ServiceArea());
    }

    public static class ServiceArea {
        public Province province = new Province();
        public City city = new City();
        public District district = new District();
    }

    public static class Province {
        public String id = "string";
        public String label = "string";
    }

    public static class City {
        public String id = "string";
        public String label = "string";
    }

    public static class District {
        public String id = "string";
        public String label = "string";
    }

    public static class BusinessHour {
        public Hours hours = new Hours();
        public List<SpecialHours> specialHours = Arrays.asList(new SpecialHours());
        public List<AdditionalHours> addMoreHours = Arrays.asList(new AdditionalHours());
    }

    public static class Hours {
        public String type = "string";
        public Map<String, DaySchedule> days = new HashMap<>();

        public Hours() {
            days.put("monday", new DaySchedule());
            days.put("tuesday", new DaySchedule());
            days.put("wednesday", new DaySchedule());
            days.put("thursday", new DaySchedule());
            days.put("friday", new DaySchedule());
            days.put("saturday", new DaySchedule());
            days.put("sunday", new DaySchedule());
        }
    }

    public static class DaySchedule {
        public boolean isClose = true;
        public List<Shift> shift = Arrays.asList(new Shift());
    }

    public static class Shift {
        public String openTime = "string";
        public String closeTime = "string";
    }

    public static class SpecialHours {
        public String date = "string";
        public List<Shift> shift = Arrays.asList(new Shift());
        public boolean isClose = true;
    }

    public static class AdditionalHours {
        public String type = "string";
        public Map<String, DaySchedule> days = new HashMap<>();

        public AdditionalHours() {
            days.put("monday", new DaySchedule());
            days.put("tuesday", new DaySchedule());
            days.put("wednesday", new DaySchedule());
            days.put("thursday", new DaySchedule());
            days.put("friday", new DaySchedule());
            days.put("saturday", new DaySchedule());
            days.put("sunday", new DaySchedule());
        }
    }

    public static class More {
        public boolean identifiesWomenOwned = true;
        public boolean assistiveHearingLoop = true;
        public boolean wheelchairAccessibleRestroom = true;
        public boolean wheelchairAccessibleSeating = true;
        public boolean genderNeutralRestroom = true;
        public boolean onsiteServiceAvailable = true;
    }

    public About about = new About();
    public ContactInfo contactInfo = new ContactInfo();
    public LocationArea locationArea = new LocationArea();
    public BusinessHour businessHour = new BusinessHour();
    public More more = new More();
    public int progress = 0;
    public int status = 0;

    public static void main(String[] args) {
        BusinessInfo businessInfo = new BusinessInfo();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(businessInfo);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
