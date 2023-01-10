package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Contacts | Vaadin CRM")
@Route(value = "")
public class ListView extends VerticalLayout {

    private final CrmService service;

    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();
    ContactForm contactForm;

    public ListView(CrmService service) {
        this.service = service;
        addClassName("List-view");
        setSizeFull();

        configuredGrid();
        configuredForm();
        add(
                getToolbar(),
                getContent()
        );
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, contactForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, contactForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configuredForm() {
        contactForm = new ContactForm(
                service.findAllCompanies(), service.findAllStatuses());
        contactForm.setWidth("25em");
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by Name.");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener( e -> updateList());

        Button addContactButton = new Button("Add Contact");
        addContactButton.addClickListener(event -> addContact());

        HorizontalLayout toolBar = new HorizontalLayout(filterText, addContactButton);
        toolBar.addClassName("toolbar");
        return  toolBar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    public void configuredGrid(){
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName","lastName", "email");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");

        grid.getColumns().forEach(contactColumn -> contactColumn.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
    }

    private void editContact(Contact contact) {
        if(contact == null){
            closeEditor();
        }else{
            contactForm.setContact(contact);
            contactForm.setVisible(true);
            addClassName("editing");
        }
    }



      /*
     Button button = new Button("Click Me");
     TextField txtName = new TextField("Name");

     HorizontalLayout horizontalLayout = new HorizontalLayout(txtName,button);
     horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
     button.addClickListener(click -> Notification.show("Hello "+txtName.getValue()));

     add(horizontalLayout);*/
}
