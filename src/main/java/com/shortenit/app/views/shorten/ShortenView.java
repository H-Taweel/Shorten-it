package com.shortenit.app.views.shorten;

import com.shortenit.app.model.CreateShortURLRequest;
import com.shortenit.app.model.CreateShortURLResponse;
import com.shortenit.app.model.ExpandShortURLResponse;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
        // Adding the logo at the top
        Image img = new Image("themes/shortenit/components/Images/Logo.png", "Logo");
        img.setWidth("200px");
        img.setHeight("150px");

        TextField textField = new TextField();
        Button buttonPrimary = new Button("Shorten-it");
        Button buttonSecondary = new Button("Expand-it");

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);

        textField.setLabel("Paste your URL");
        textField.setWidth("400px");
        textField.setHeight("60px");

        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setWidth("min-content");

        getContent().add(img, textField, buttonPrimary, buttonSecondary);

        // Handle URL shortening
        buttonPrimary.addClickListener(event -> {
            String inputUrl = textField.getValue();
            try {
                sendInputToController(inputUrl);
            } catch (Exception e) {
                Notification.show("Invalid URL format: " + inputUrl, 10000, Notification.Position.MIDDLE);
            }
        });

        // Handle URL expansion
        buttonSecondary.addClickListener(event -> {
            String shortURLKey = textField.getValue();
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

            Notification.show("Shortened URL: " + (response != null ? response.shortURL() : null), 10000, Notification.Position.MIDDLE);
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage(), 10000, Notification.Position.MIDDLE);
        }
    }

    private void expandShortURL(String shortURLKey) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ExpandShortURLResponse response = restTemplate.exchange(
                    "http://localhost:8080/api/v1/url-shortener/expand/" + shortURLKey,
                    HttpMethod.GET,
                    null,
                    ExpandShortURLResponse.class
            ).getBody();

            // Redirect to the original long URL in a new tab
            String originalURL = response != null ? response.longURL().toString() : null;
            getUI().ifPresent(ui -> ui.getPage().executeJs("window.open($0, '_blank')", originalURL));

        } catch (Exception e) {
            Notification.show("Error expanding URL: " + e.getMessage(), 10000, Notification.Position.MIDDLE);
        }
    }
}
