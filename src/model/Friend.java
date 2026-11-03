package model;

public class Friend 
{
    private String name;
    private String imagePath;
    private QuestionCategory[] specialties;

    public Friend(String name, String imagePath, QuestionCategory... specialties) 
    {
        this.name = name;
        this.imagePath = imagePath;
        this.specialties = specialties;
    }

    public String getName() 
    {
        return name;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public QuestionCategory[] getSpecialties() 
    {
        return specialties;
    }

    public boolean isExpertIn(QuestionCategory category)
    {
        for(QuestionCategory specialty : specialties)
        {
            if(specialty == category)
            {
                return true;
            }
        }

        return false;
    }
}