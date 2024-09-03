package com.shortenit.app.views.home;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Home")
@Route(value = "")
@RouteAlias(value = "")
public class HomeView extends Composite<VerticalLayout> {

    public HomeView() {
        LoginForm loginForm = new LoginForm();
        Anchor link = new Anchor();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
//        loginForm.setWidthFull();
        link.setText("Skip");
        link.setHref("/shorten");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, link);
        link.setWidth("30px");
        link.setHeight("26px");
        getContent().add(loginForm);
        getContent().add(link);
    }
}
