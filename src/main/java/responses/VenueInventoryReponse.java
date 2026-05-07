package responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueInventoryResponse {
    private Long VenueId;
    private String venueName;
    private Long totalCapacity;
}
