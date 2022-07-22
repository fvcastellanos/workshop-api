package net.cavitos.workshop.search.typesense;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.cavitos.workshop.domain.model.web.Contact;
import net.cavitos.workshop.search.ContactSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.typesense.api.Client;
import org.typesense.model.SearchParameters;

import java.util.HashMap;
import java.util.Map;

@Component
public class TypesenseContactSearch implements ContactSearch {

    private static final String COLLECTION_NAME = "contacts";

    private final Client typesenseClient;

    @Autowired
    public TypesenseContactSearch(final Client typesenseClient) {

        this.typesenseClient = typesenseClient;
    }

    @Override
    public Page<Map<String, Object>> search(String text, String active, int page, int size) {

        try {

            final var searchParameters = new SearchParameters();
            searchParameters.q(text)
                    .queryBy("code,name,taxId")
                    .filterBy("active:" + active + " && tenant:" + "resta")
                    .perPage(size)
                    .page(page);

            var searchResult = typesenseClient.collections(COLLECTION_NAME)
                    .documents()
                    .search(searchParameters);

            return null;

        } catch (Exception exception) {

            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public void upsert(final String tenant, final Contact contact) {

        try {
            var documentMap = new HashMap<String, Object>();

            documentMap.put("code", contact.getCode());
            documentMap.put("name", contact.getName());
            documentMap.put("type", contact.getType());
            documentMap.put("active", contact.getActive());
            documentMap.put("taxId", contact.getTaxId());
            documentMap.put("contact", contact.getContact());
            documentMap.put("description", contact.getDescription());
            documentMap.put("tenant", tenant);

            typesenseClient.collections(COLLECTION_NAME)
                    .documents()
                    .upsert(documentMap);

        } catch (Exception exception) {

            exception.printStackTrace();
        }

    }
}
