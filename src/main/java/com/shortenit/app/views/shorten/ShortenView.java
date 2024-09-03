package com.shortenit.app.views.shorten;

import com.shortenit.app.model.CreateShortURLRequest;
import com.shortenit.app.model.CreateShortURLResponse;
import com.shortenit.app.model.ExpandShortURLResponse;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@PageTitle("Shorten & Expand")
@Route(value = "shorten")
public class ShortenView extends Composite<VerticalLayout> {

    public ShortenView() {
        H1 h1 = new H1("Shorten-it & Expand-it");
        MessageInput messageInputShorten = new MessageInput();
        MessageInput messageInputExpand = new MessageInput();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);

        h1.setWidth("max-content");
        messageInputShorten.setWidth("min-content");
        messageInputExpand.setWidth("min-content");

        // Labeling the input fields
        messageInputShorten.getElement().setProperty("placeholder", "Enter URL to shorten...");
        messageInputExpand.getElement().setProperty("placeholder", "Enter short URL key to expand...");

        getContent().add(h1, messageInputShorten, messageInputExpand);

        // Handle URL shortening
        messageInputShorten.addSubmitListener(event -> {
            String inputUrl = event.getValue();
            try {
                sendInputToController(inputUrl);
            } catch (Exception e) {
                Notification.show("Invalid URL format: " + inputUrl, 10000, Notification.Position.MIDDLE);
            }
        });

        // Handle URL expansion
        messageInputExpand.addSubmitListener(event -> {
            String shortURLKey = event.getValue();
            try {
                expandShortURL(shortURLKey);
            } catch (Exception e) {
                Notification.show("Error expanding short URL key: " + shortURLKey, 10000, Notification.Position.MIDDLE);
            }
        });
    }

    private void sendInputToController(String inputUrl) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(inputUrl);
        CreateShortURLRequest requestPayload = new CreateShortURLRequest(uri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateShortURLRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

        try {
            CreateShortURLResponse response = restTemplate.exchange(
                    "http://localhost:8080/api/v1/url-shortener/create",
                    HttpMethod.POST,
                    requestEntity,
                    CreateShortURLResponse.class
            ).getBody();

            Notification.show("Shortened URL: " + response.shortURL(), 10000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage(), 10000, Notification.Position.MIDDLE);
        }
    }

    private void expandShortURL(String shortURLKey) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Send GET request to the REST controller endpoint
            ExpandShortURLResponse response = restTemplate.exchange(
                    "http://localhost:8080/api/v1/url-shortener/expand/" + shortURLKey,
                    HttpMethod.GET,
                    null,
                    ExpandShortURLResponse.class
            ).getBody();

            // Redirect to the original long URL
            String originalURL = response.longURL().toString();
            getUI().ifPresent(ui -> ui.getPage().executeJs("window.open($0, '_blank')", originalURL));
//            getUI().ifPresent(ui -> ui.getPage().setLocation(originalURL));

        } catch (Exception e) {
            Notification.show("Error expanding URL: " + e.getMessage(), 10000, Notification.Position.MIDDLE);
        }
    }
}
