class Token implements Cloneable{
    private String init;
    private String accesControl;
    private String end;

    private String frameControl;
    private String destinatioAddress;
    private String sourceAddres;
    private String data;
    private String frameStatus;


    public Token(){
        cleanToken();
    }


    public void addMessage( String frameControl, String sourceAddres , String destinatioAddress, String data, String frameStatus){
        this.accesControl = "1";
        this.frameControl = frameControl;
        this.destinatioAddress = destinatioAddress;
        this.sourceAddres = sourceAddres;
        this.data = data;
        this.frameStatus = frameStatus;
    }

    public void cleanToken(){
        this.init = "1";
        this.accesControl = "0";
        this.end = "1";
        this.frameControl = "0";
        this.destinatioAddress ="0";
        this.sourceAddres ="0";
        this.data = "0";
        this.frameStatus ="0";
    }
    
    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getAccesControl() {
        return accesControl;
    }

    public void setAccesControl(String accesControl) {
        this.accesControl = accesControl;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFrameControl() {
        return frameControl;
    }

    public void setFrameControl(String frameControl) {
        this.frameControl = frameControl;
    }

    public String getDestinatioAddress() {
        return destinatioAddress;
    }

    public void setDestinatioAddress(String destinatioAddress) {
        this.destinatioAddress = destinatioAddress;
    }

    public String getSourceAddres() {
        return sourceAddres;
    }

    public void setSourceAddres(String sourceAddres) {
        this.sourceAddres = sourceAddres;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrameStatus() {
        return frameStatus;
    }

    public void setFrameStatus(String frameStatus) {
        this.frameStatus = frameStatus;
    }

    @Override
    public String toString() {
        if(accesControl.equals("0"))
        return "[ "+ init +", "+ accesControl +", "+end+" ]";
        else 
        return "[ "
        + init +", "+ accesControl +", "+frameControl +", " +destinatioAddress +", "+sourceAddres +", "+ data +", "+ end+ ", "+frameStatus+"]";
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


}