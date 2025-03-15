package belajar;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class BusinessInfoLombok {
    private About about;
    private ContactInfo contactInfo;
    private LocationArea locationArea;
    private BusinessHour businessHour;
    private More more;
    private int progress;
    private int status;

    @Data
    public static class About {
        private String locationName;
        private LocationCategory locationCategory;
        private String description;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private String openDate;
    }

    @Data
    public static class LocationCategory {
        private String primary;
        private List<String> additional;
    }

    @Data
    public static class ContactInfo {
        private List<PhoneNumber> phoneNumber;
        private String website;
        private List<SocialProfile> socialProfile;
    }

    @Data
    public static class PhoneNumber {
        private String country;
        private String number;
    }

    @Data
    public static class SocialProfile {
        private String type;
        private String url;
    }

    @Data
    public static class LocationArea {
        private boolean showAddress;
        private String country;
        private String primaryAddress;
        private String additionalAddress;
        private String postalCode;
        private String provinceId;
        private String cityId;
        private String coverageAreaId;
        private double latitude;
        private double longitude;
        private List<ServiceArea> serviceAreaId;
    }

    @Data
    public static class ServiceArea {
        private Province province;
        private City city;
        private District district;
    }

    @Data
    public static class Province {
        private String id;
        private String label;
    }

    @Data
    public static class City {
        private String id;
        private String label;
    }

    @Data
    public static class District {
        private String id;
        private String label;
    }

    @Data
    public static class BusinessHour {
        private Hours hours;
        private List<SpecialHours> specialHours;
        private List<AdditionalHours> addMoreHours;
    }

    @Data
    public static class Hours {
        private String type;
        private Map<String, DaySchedule> days;
    }

    @Data
    public static class DaySchedule {
        private boolean isClose;
        private List<Shift> shift;
    }

    @Data
    public static class Shift {
        private String openTime;
        private String closeTime;
    }

    @Data
    public static class SpecialHours {
        private String date;
        private List<Shift> shift;
        private boolean isClose;
    }

    @Data
    public static class AdditionalHours {
        private String type;
        private Map<String, DaySchedule> days;
    }

    @Data
    public static class More {
        private boolean identifiesWomenOwned;
        private boolean assistiveHearingLoop;
        private boolean wheelchairAccessibleRestroom;
        private boolean wheelchairAccessibleSeating;
        private boolean genderNeutralRestroom;
        private boolean onsiteServiceAvailable;
    }

    public static void main(String[] args) throws Exception {
        BusinessInfoLombok businessInfo = new BusinessInfoLombok();
        businessInfo.setProgress(10);
        businessInfo.setStatus(1);
        
        About about = new About();
        about.setLocationName("Example Location");
        about.setDescription("This is a description");
        about.setOpenDate("2025-01-23T08:30:05+00:00");
        
        businessInfo.setAbout(about);

        // Convert to JSON and print
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(businessInfo);
        System.out.println(jsonOutput);
    }
}
