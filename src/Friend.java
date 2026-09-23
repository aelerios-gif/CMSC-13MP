public class Friend 
{
    private String name;
    private String specialty;
    private int reliability;

    public Friend(String name, String specialty, int reliability) 
    {
        this.name = name;
        this.specialty = specialty;
        this.reliability = reliability;
    }

    public String getName() 
    {
        return name;
    }

    public String getSpecialty() 
    {
        return specialty;
    }

    public int getReliability() 
    {
        return reliability;
    }
}