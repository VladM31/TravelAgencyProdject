package nure.knt.forms.filter;

import nure.knt.database.idao.temporary.IDAOMessage;
import nure.knt.entity.enums.Role;
import nure.knt.entity.subordinate.MessageShortData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MessageShowFilter {
    private Role byRole;
    private String byNameSendler;
    private String byNameMessage;
    private String byStartSendDate;
    private String byEndSendDate;
    private boolean byRead;
    private boolean byNotRead;

    public MessageShowFilter(Role byRole, String byNameSendler, String byNameMessage, String byStartSendDate, String byEndSendDate, boolean byRead, boolean byNotRead) {
        this.byRole = byRole;
        this.byNameSendler = byNameSendler;
        this.byNameMessage = byNameMessage;
        this.byStartSendDate = byStartSendDate;
        this.byEndSendDate = byEndSendDate;
        this.byRead = byRead;
        this.byNotRead = byNotRead;
    }

    public MessageShowFilter() {
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

    public String getByStartSendDate() {
        return byStartSendDate;
    }

    public String getStartDate(){
        return byStartSendDate.replace('T',' ');
    }

    public String getEndDate(){
        return byEndSendDate.replace('T',' ');
    }

    public void setByStartSendDate(String byStartSendDate) {
        this.byStartSendDate = byStartSendDate;
    }

    public String getByEndSendDate() {
        return byEndSendDate;
    }

    public void setByEndSendDate(String byEndSendDate) {
        this.byEndSendDate = byEndSendDate;
    }

    public boolean getByRead() {
        return byRead;
    }

    public void setByRead(boolean byRead) {
        this.byRead = byRead;
    }

    public boolean getByNotRead() {
        return byNotRead;
    }

    public void setByNotRead(boolean byNotRead) {
        this.byNotRead = byNotRead;
    }

    public static boolean NOT_EMPTY = false;
    public static int START_DATE = 0;
    public static int END_DATE = 1;

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

        list = HandlerFilter.checkDate(this.getStartDate(),this.getEndDate(),list,
                (d1,d2) -> idaoMessage.findMSDByToWhomAndSendDateBetween(toWhom,d1,d2),
                (d1,d2) ->filterList.add((msd) -> msd.getSendDate().isAfter(d1)  && msd.getSendDate().isBefore(d2)),
                (d) -> idaoMessage.findMSDByToWhomAndSendDateAfterAndEquals(toWhom, d),
                (d) ->  filterList.add((msd) -> msd.getSendDate().isAfter(d) ),
                (d) -> idaoMessage.findMSDByToWhomAndSendDateBeforeAndEquals(toWhom, d),
                (d) -> filterList.add((msd) -> msd.getSendDate().isBefore(d)));


        if(list == HandlerFilter.LIST_IS_NOT_CREATED_FROM_DATABASE){
            return idaoMessage.findMessageShortDataAllByToWhom(toWhom);
        }
        if (list.isEmpty() || filterList.isEmpty()){
            return list;
        }

        return  list.stream()
                .collect(
                        Collectors.filtering((msd)-> HandlerFilter.predicateList(filterList,msd)
                                ,Collectors.toList()));
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

