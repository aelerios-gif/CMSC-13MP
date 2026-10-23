public class Friend 
{
    private String name;
    private QuestionCategory[] specialties;

    public Friend(String name, QuestionCategory... specialties) 
    {
        this.name = name;
        this.specialties = specialties;
    }

    public String getName() 
    {
        return name;
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