package com.example.demo.forms.filter;

import com.example.demo.database.idao.temporary.IDAOMessage;
import com.example.demo.entity.important.Role;
import com.example.demo.entity.subordinate.MessageShortData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    public List<LocalDateTime> getAllDate(){
        if (this.byStartSendDate.isEmpty() || this.byEndSendDate.isEmpty()){
            return List.of();
        }
        List<LocalDateTime> list = FilterHendler.getListThatIsSorted(
                LocalDateTime.parse(this.getStartDate(),FilterHendler.STRING_TO_DATE_TIME_FORMAT),
                LocalDateTime.parse(this.getEndDate(),FilterHendler.STRING_TO_DATE_TIME_FORMAT));
        //System.out.println(list);

        return list;
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

        if (this.byRead != this.byNotRead){
            if(list == null){
                list = idaoMessage.findMSDByToWhomAndItWasRead(toWhom,this.byRead);
            }else{
                filterList.add((msd) -> msd.isItWasRead() == this.byRead);
            }
        }

        if (this.byNameSendler.isEmpty() == NOT_EMPTY){
            this.byNameSendler = this.byNameSendler.trim();
            if(list == null){
                list = idaoMessage.findMSDByToWhomAndSendlerNameContaining(toWhom,this.byNameSendler);
            }else{
                filterList.add((msd) -> msd.getSendlerName().contains(this.byNameSendler));
            }
        }

        if(this.byNameMessage.isEmpty() == NOT_EMPTY){
            this.byNameMessage = this.byNameMessage.trim();
            if(list == null){
                list = idaoMessage.findMSDByToWhomAndNameMessageContaining(toWhom,this.byNameMessage);
            }else{
                filterList.add((msd) -> msd.getMessageName().contains(this.byNameMessage));
            }
        }
        //System.out.println(this.byStartSendDate);
        List<LocalDateTime> dateList = this.getAllDate();
        if(dateList.isEmpty() == NOT_EMPTY){
           if(list == null){
                list = idaoMessage.findMSDByToWhomAndSendDateBetween(toWhom,dateList.get(START_DATE),dateList.get(END_DATE));
           }else{
               filterList.add((msd) -> msd.getSendDate().isAfter(dateList.get(START_DATE))
                       && msd.getSendDate().isBefore(dateList.get(END_DATE)));
            }
        } else if(this.byStartSendDate.isEmpty() == NOT_EMPTY){
            if(list == null){
                list = idaoMessage.findMSDByToWhomAndSendDateAfterAndEquals(toWhom, LocalDateTime.parse(this.getStartDate(),FilterHendler.STRING_TO_DATE_TIME_FORMAT));
            }else{
                filterList.add((msd) -> msd.getSendDate().isAfter( LocalDateTime.parse(this.getStartDate(),FilterHendler.STRING_TO_DATE_TIME_FORMAT)) );
            }
        } else if(this.byEndSendDate.isEmpty() == NOT_EMPTY){
            if(list == null){
                list = idaoMessage.findMSDByToWhomAndSendDateBeforeAndEquals(toWhom, LocalDateTime.parse(this.getEndDate(),FilterHendler.STRING_TO_DATE_TIME_FORMAT));
            }else{
                filterList.add((msd) -> msd.getSendDate().isBefore( LocalDateTime.parse(this.getEndDate(),FilterHendler.STRING_TO_DATE_TIME_FORMAT)));
            }
        }

        if(list == null){
            return idaoMessage.findMessageShortDataAllByToWhom(toWhom);
        }
        if (list.isEmpty() || filterList.isEmpty()){
            return list;
        }

        return  list.stream()
                .collect(
                        Collectors.filtering((msd)-> FilterHendler.predicateList(filterList,msd)
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

