import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private Double latitude = 0d;
    private Double longitude = 0d;
    private String countryCode = "";
    private String countryName = "";
    private String county = "";
    private String city = "";
    private String district = "";
    private String street = "";
    private String houseNumber = "";
    private String description = "";
}
