package TennisScoreboard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchDTO {

    int page;

    int size;

    String search;


    public SearchDTO(int page, int size) {
        this.size = size;
        this.page = page;
    }
}
