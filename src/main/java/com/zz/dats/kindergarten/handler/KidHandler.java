package com.zz.dats.kindergarten.handler;

import com.zz.dats.kindergarten.db.entity.FamilyKidsEntity;
import com.zz.dats.kindergarten.db.entity.FamilyNameEntity;
import com.zz.dats.kindergarten.db.entity.KidEntity;
import com.zz.dats.kindergarten.db.repository.KidFamilyRepository;
import com.zz.dats.kindergarten.db.repository.KidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class KidHandler {
    public static final String kidNamePattern = "\\S{3,32}";
    public static final String kidLastNamePattern = "\\S{3,64}";
    public static final String kidPersonalCodePattern = "\\d{6}-\\d{5}";

    public static final String kidNameErrorMessage = "vārdam jābūt no 3 līdz 32 simboliem garam, tikai burtiem";
    public static final String kidLastNameErrorMessage = "uzvārdam jābūt no 3 līdz 64 simboliem garam, tikai burtiem";
    public static final String kidPersonalCodeErrorMessage = "personas kodam jābut xxxxxx-xxxxx, kur x ir jebkurš cipars";
    KidRepository kidRepository;
    KidFamilyRepository kidFamilyRepository;

    @Autowired
    public KidHandler(
        KidRepository kidRepository,
        KidFamilyRepository kidFamilyRepository
    ) {
        this.kidRepository = kidRepository;
        this.kidFamilyRepository = kidFamilyRepository;
    }

    public void setFamily(Integer kidId, FamilyNameEntity familyNameEntity) {
        KidEntity entity = getKidEntity(kidId);

        if (familyNameEntity == null) {
            if (entity.getFamilyEntity() != null) {
                kidFamilyRepository.delete(entity.getFamilyEntity());
            }

            return;
        }

        FamilyKidsEntity familyKidsEntity = entity.getFamilyEntity() != null ? entity.getFamilyEntity() : new FamilyKidsEntity();

        familyKidsEntity.setKidByKidId(entity);
        familyKidsEntity.setFamilyNamesByFamilyId(familyNameEntity);
        entity.setFamilyEntity(familyKidsEntity);

        this.kidFamilyRepository.save(familyKidsEntity);
        this.kidRepository.save(entity);
    }

    public boolean isNameValid(String name) {
        return name.matches(kidNamePattern);
    }

    public boolean isLastNameValid(String lastName) {
        return lastName.matches(kidLastNamePattern);
    }

    public boolean isPersonalCodeValid(String personalCode) {
        return personalCode.matches(kidPersonalCodePattern);
    }

    public void addKid(
        String name,
        String lastName,
        String personalCode
    ) {
        if (! (this.isNameValid(name) && this.isLastNameValid(lastName) && this.isPersonalCodeValid(personalCode))) {
            throw new IllegalArgumentException("please pass only valid parameters");
        }

        KidEntity kidEntity = new KidEntity();

        kidEntity.setName(name);
        kidEntity.setLastName(lastName);
        kidEntity.setPersonalCode(personalCode);

        this.kidRepository.save(kidEntity);
    }

    private KidEntity getKidEntity(Integer kidId) {
        Optional<KidEntity> kid = this.kidRepository.findById(kidId);

        if (kid.isEmpty()) {
            throw new IllegalArgumentException(String.format("nav atrasts bērns ar id %d", kidId));
        }

        return kid.get();
    }
}
