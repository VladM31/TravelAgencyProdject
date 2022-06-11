package nure.knt.forms.filter;

import nure.knt.database.idao.temporary.IDAOMessage;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.MessageShortData;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterMessageShow {
    private Role byRole;
    private String byNameSendler;
    private String byNameMessage;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime byStartSendDate,byEndSendDate;
    private boolean byRead,byNotRead;

    public FilterMessageShow(Role byRole, String byNameSendler, String byNameMessage,
                             LocalDateTime byStartSendDate, LocalDateTime byEndSendDate, boolean byRead, boolean byNotRead) {
        this.byRole = byRole;
        this.byNameSendler = byNameSendler;
        this.byNameMessage = byNameMessage;
        this.byStartSendDate = byStartSendDate;
        this.byEndSendDate = byEndSendDate;
        this.byRead = byRead;
        this.byNotRead = byNotRead;
    }

    public FilterMessageShow() {
    }

    public Role getByRole() {
        return byRole;
    }

    public void setByRole(Role byRole) {
        this.byRole = byRole;
    }

    public String getByNameSendler() {
        return byNameSendler;
    }

    public void setByNameSendler(String byNameSendler) {
        this.byNameSendler = byNameSendler;
    }

    public String getByNameMessage() {
        return byNameMessage;
    }

    public void setByNameMessage(String byNameMessage) {
        this.byNameMessage = byNameMessage;
    }


    public String getStartDate(){
        return byStartSendDate.toString().replace('T',' ');
    }

    public String getEndDate(){
        return byEndSendDate.toString().replace('T',' ');
    }

    public LocalDateTime getByStartSendDate() {
        return byStartSendDate;
    }

    public void setByStartSendDate(LocalDateTime byStartSendDate) {
        this.byStartSendDate = byStartSendDate;
    }

    public LocalDateTime getByEndSendDate() {
        return byEndSendDate;
    }

    public void setByEndSendDate(LocalDateTime byEndSendDate) {
        this.byEndSendDate = byEndSendDate;
    }

    public boolean isByRead() {
        return byRead;
    }

    public boolean isByNotRead() {
        return byNotRead;
    }

    public void setByRead(boolean byRead) {
        this.byRead = byRead;
    }

    public void setByNotRead(boolean byNotRead) {
        this.byNotRead = byNotRead;
    }

    public List<MessageShortData> filtering(long toWhom, IDAOMessage idaoMessage){
        List<MessageShortData> list = null;

        List<Predicate<MessageShortData>> filterList = new ArrayList<>();

        if(this.byRole != Role.ALL){
            list = idaoMessage.findMSDByToWhomAndRole(toWhom,this.byRole);
        }

        list = HandlerFilter.checkTwoBooleanForOneState(this.byRead,this.byNotRead,list,
                (state) -> idaoMessage.findMSDByToWhomAndItWasRead(toWhom,state),
                (state) -> filterList.add(msd -> msd.isItWasRead() == state.booleanValue() ));

        list = HandlerFilter.checkString(this.byNameSendler,list,
                (nameSendler)-> idaoMessage.findMSDByToWhomAndSendlerNameContaining(toWhom,nameSendler),
                (nameSendler) ->filterList.add((msd) -> msd.getSendlerName().contains(nameSendler)));

        list = HandlerFilter.checkString(this.byNameMessage,list,
                (nameMessage)-> idaoMessage.findMSDByToWhomAndNameMessageContaining(toWhom,nameMessage),
                (nameMessage) ->filterList.add((msd) -> msd.getMessageName().contains(nameMessage)));

        list = HandlerFilter.checkDateTime(this.byStartSendDate,this.byEndSendDate,
                HandlerFilter.MIN_LOCAL_DATE_TIME,HandlerFilter.MAX_LOCAL_DATE_TIME,list,
                (d1,d2) -> idaoMessage.findMSDByToWhomAndSendDateBetween(toWhom,d1,d2),
                (d1,d2) ->filterList.add((msd) -> msd.getSendDate().isAfter(d1) && msd.getSendDate().isBefore(d2)));


        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return idaoMessage.findMessageShortDataAllByToWhom(toWhom);
        }

        return HandlerFilter.endFiltering(list,filterList);
    }

    @Override
    public String toString() {
        return "MessageShowFilter{" +
                "byRole=" + byRole +
                ", byNameSendler='" + byNameSendler + '\'' +
                ", byNameMessage='" + byNameMessage + '\'' +
                ", byStartSendDate=" + byStartSendDate +
                ", byEndSendDate=" + byEndSendDate +
                ", byRead=" + byRead +
                ", byNotRead=" + byNotRead +
                '}';
    }
}

