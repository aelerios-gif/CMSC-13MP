public class Friend 
{
    private String name;
    private QuestionCategory specialty;

    public Friend(String name, QuestionCategory specialty) 
    {
        this.name = name;
        this.specialty = specialty;
    }

    public String getName() 
    {
        return name;
    }

    public QuestionCategory getSpecialty() 
    {
        return specialty;
    }
}