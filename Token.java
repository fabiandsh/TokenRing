class Token{
    private byte init;
    private byte accesControl;
    private byte end;

    public Token(){
        init = 1;
        accesControl = 1;
        end = 1;
    }
    
    @Override
    public String toString() {
        return "[ "+ init +", "+ accesControl +", "+end+" ]";
    }

}