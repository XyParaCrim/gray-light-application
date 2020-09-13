package gray.light.support.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageChunk<T> {

    private int pages;

    private int count;

    private int total;

    private List<T> items;

}
