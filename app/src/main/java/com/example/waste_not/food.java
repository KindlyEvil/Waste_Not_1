package com.example.waste_not;

public class food {
    private String OrganisationName,foodDescription;
    private String   contactInfo;

    public food() {

    }

    public food(String organisationName, String foodDescription, String contactInfo) {
        this.OrganisationName = organisationName;
        this.foodDescription = foodDescription;
        this.contactInfo = contactInfo;
    }

    public void setOrganisationName(String organisationName) {
        OrganisationName = organisationName;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getOrganisationName() {
        return OrganisationName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
