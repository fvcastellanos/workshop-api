package net.cavitos.workshop.search;

import net.cavitos.workshop.domain.model.web.Contact;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ContactSearch {

    Page<Map<String, Object>> search(String text, String active, int page, int size);
    void upsert(String tenant, Contact contact);
}
