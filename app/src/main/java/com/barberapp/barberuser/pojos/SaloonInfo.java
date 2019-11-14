package com.barberapp.barberuser.pojos;

public class SaloonInfo {
    private SaloonServicesResponse saloonServicesResponse;
    private MemberReponse memberReponse;

    public SaloonServicesResponse getSaloonServicesResponse() {
        return saloonServicesResponse;
    }

    public void setSaloonServicesResponse(SaloonServicesResponse saloonServicesResponse) {
        this.saloonServicesResponse = saloonServicesResponse;
    }

    public MemberReponse getMemberReponse() {
        return memberReponse;
    }

    public void setMemberReponse(MemberReponse memberReponse) {
        this.memberReponse = memberReponse;
    }
}
