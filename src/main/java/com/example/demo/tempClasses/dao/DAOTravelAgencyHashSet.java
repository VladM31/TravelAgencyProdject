package com.example.demo.tempClasses.dao;

import com.example.demo.database.idao.entity.IDAOTravelAgency;
import com.example.demo.entity.enums.Role;
import com.example.demo.entity.important.TravelAgency;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component("DAOTravelAgency")
public class DAOTravelAgencyHashSet implements IDAOTravelAgency<TravelAgency> {

    private static Set<TravelAgency> table = new HashSet<>();

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        int toDelete = 0;
        TravelAgency temp = null;
        for (Iterator<TravelAgency> it = table.iterator(); it.hasNext(); ) {
            temp = it.next();
            for (long id : ids) {
                if (id == temp.getId()) {
                    ++toDelete;
                    it.remove();
                    break;
                }
            }
        }
        return toDelete;
    }

    @Override
    public int deleteAllByEntity(Iterable<TravelAgency> entities) {
        int wasSize = this.table.size();
        for (TravelAgency cust : entities) {
            table.remove(cust);
        }
        return wasSize - this.table.size();
    }

    @Override
    public int deleteByEntity(TravelAgency entity) {
        return Boolean.compare(this.table.remove(entity),false);
    }

    @Override
    public int deleteById(Long id) {
        for (Iterator<TravelAgency> it = table.iterator(); it.hasNext(); ) {
            if (it.next().getId().equals(id)) {
                it.remove();
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int deleteAll() {
        int wasSize = this.table.size();
        this.table.clear();
        return wasSize;
    }

    @Override
    public List<TravelAgency> findAll() {
        return new ArrayList<>(this.table);
    }

    @Override
    public List<TravelAgency> findAllById(Iterable<Long> ids) {
        return this.table.stream().filter( i ->{
            for(long id:ids)
            {
                if(id == i.getId())
                {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public TravelAgency findOneById(Long id) {
        for (TravelAgency cust : table) {
            if (cust.getId().equals(id)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public boolean saveAll(Iterable<TravelAgency> entities) {
        int wasSize = this.table.size();
        entities.forEach(table::add);
        return Integer.compare(wasSize, table.size()) != 0;
    }

    @Override
    public boolean save(TravelAgency entity) {
        return this.table.add(entity);
    }

    @Override
    public int updateAllById(Iterable<TravelAgency> entities) {
        return 0;
    }

    @Override
    public int updateOneById(TravelAgency entity) {
        return 0;
    }

    @Override
    public long size() {
        return this.table.size();
    }

    @Override
    public TravelAgency findByNumber(long number) {
        for (TravelAgency cust : this.table) {
            if (cust.getNumber() == number) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public TravelAgency findByEmail(String email) {
        for (TravelAgency cust : this.table) {
            if (cust.getEmail().equals(email)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public TravelAgency findByUsername(String username) {
        for (TravelAgency cust : this.table) {
            if (cust.getUsername().equals(username)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public List<TravelAgency> findByPassword(String password) {
        return this.table.stream().filter(i -> i.getPassword().equals(password)).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByDateRegistration(LocalDateTime dataRegistration) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().equals(dataRegistration))
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByDateRegistrationAfter(LocalDateTime dataRegistration) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().isAfter(dataRegistration))
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByDateRegistrationBefore(LocalDateTime dataRegistration) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().isBefore(dataRegistration))
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return this.table
                .stream()
                .filter(i-> i.getDateRegistration().isAfter(start) && i.getDateRegistration().isBefore(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByActive(boolean active) {
        return this.table
                .stream()
                .filter(i-> i.isActive() == active)
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByRole(Role role) {
        return this.table
                .stream()
                .filter(i-> i.getRole().equals(role))
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByCountry(String country) {
        return this.table
                .stream()
                .filter(i-> i.getCountry().equals(country))
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByEmailLike(String piece) {
        return null;
    }

    @Override
    public List<TravelAgency> findByEmailContaining(String start) {
        return null;
    }

    @Override
    public List<TravelAgency> findByTravelAgencyIdIn(Iterable<Long> ids) {
        return table.stream().filter( i->{
            for (long ta: ids) {
                if(i.getTravelId() == ta){
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByTravelAgencyObjectIdIn(Iterable<TravelAgency> ids) {
        return  table.stream().filter( i -> {
            for (TravelAgency id : ids) {
                if (i.getTravelId().equals(id.getTravelId())) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public TravelAgency findByTravelAgencyId(Long id) {
        for (TravelAgency cust : table) {
            if (cust.getTravelId().equals(id)) {
                return cust;
            }
        }
        return null;
    }

    @Override
    public List<TravelAgency> findByRating(float rating) {
        return this.table.stream().filter( i -> Float.compare(i.getRating(),rating)==0).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByRatingLessThanEqual(float rating) {
        return this.table.stream().filter( i ->i.getRating()<= rating).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByRatingGreaterThanEqual(float rating) {
        return this.table.stream().filter( i ->i.getRating() >= rating).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByRatingGreaterThanEqualAndLessThanEqual(float ratingF, float ratingE) {
        return this.table.stream().filter( i ->i.getRating() >= ratingF && i.getRating() <= ratingE).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByKVED(long kved) {
        return null;
    }

    @Override
    public List<TravelAgency> findByKVEDIn(Iterable<Long> kveds) {
        return  table.stream().filter( i -> {
            for (long kved : kveds) {
                if (i.getKved() == kved) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public TravelAgency findByEGRPOY(Long egrpoy) {
        for (TravelAgency ta : table) {
            if (ta.isEgrpoy() && ta.getEgrpoy_or_rnekpn().equals(egrpoy) ) {
                return ta;
            }
        }
        return null;
    }

    @Override
    public List<TravelAgency> findByEGRPOYIn(Iterable<Long> egrpoys) {
        return  table.stream().filter( i -> {
            for (long egrpoy : egrpoys) {
                if (i.isEgrpoy() && i.getEgrpoy_or_rnekpn() == egrpoy) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByEGRPOY(boolean isEGRPOY) {
        return table.stream()
                .filter( i -> i.isEgrpoy() == isEGRPOY)
                .collect(Collectors.toList());
    }

    @Override
    public TravelAgency findByRNEKPN(Long rnekpn) {
        for (TravelAgency ta : table) {
            if (!ta.isEgrpoy() && ta.getEgrpoy_or_rnekpn().equals(rnekpn)) {
                return ta;
            }
        }
        return null;
    }

    @Override
    public List<TravelAgency> findByRNEKPNIn(Iterable<Long> rnekpns) {
        return  table.stream().filter( i -> {
            for (long rnekpn : rnekpns) {
                if (!i.isEgrpoy() && i.getEgrpoy_or_rnekpn() == rnekpn) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public TravelAgency findByAddress(String address) {
        for (TravelAgency ta : table) {
            if (ta.getAddress().equals(address)) {
                return ta;
            }
        }
        return null;
    }

    @Override
    public List<TravelAgency> findByAddressLike(String script) {
        return null;
    }

    @Override
    public List<TravelAgency> findByAllNameDirector(String allNameDirector) {
        return table.stream().filter(i -> i.getAllNameDirector().equals(allNameDirector)).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByAllNameDirectorLike(String script) {
        return null;
    }


    @Override
    public List<TravelAgency> findByUsernameOrNumberOrEmail(TravelAgency user) {
        return table.stream().filter( i -> i.getUsername().equals(user.getUsername()) ||
                i.getPassword().equals(user.getPassword()) ||
                i.getNumber() == user.getNumber() ||
                i.getEmail().equals(user.getEmail())
        ).collect(Collectors.toList());
    }

    @Override
    public List<TravelAgency> findByName(String name) {
        return null;
    }

    @Override
    public List<TravelAgency> findByNameContaining(String name) {
        return null;
    }
}
