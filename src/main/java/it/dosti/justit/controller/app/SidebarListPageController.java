package it.dosti.justit.controller.app;

import it.dosti.justit.bean.SearchBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SidebarListPageController {

    private final List<String> loremIpusm = List.of(
            "Mammelloni Samueloni",
            "Il meglio indiano di Torpigna",
            "El Gugno Maduro",
            "Bombai PC riparazione",
            "CurryRiparo"
    );

    public List<String> search(SearchBean bean) {
        String query = bean.getSearchText();
        if (query == null || query.isEmpty()) {
            return new ArrayList<>(loremIpusm);
        }
        return loremIpusm.stream()
                .filter(s -> s.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
