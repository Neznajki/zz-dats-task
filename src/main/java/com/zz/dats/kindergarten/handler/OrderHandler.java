package com.zz.dats.kindergarten.handler;

import com.zz.dats.kindergarten.db.entity.KindergartenEntity;
import com.zz.dats.kindergarten.db.entity.KindergartenKidsEntity;
import com.zz.dats.kindergarten.db.entity.QueueEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

@Service
public class OrderHandler {

    public void sortKids(KindergartenEntity kindergartenEntity)
    {
        Collection<QueueEntity> kidEntityCollection = kindergartenEntity.getQueuesById();
        Comparator<QueueEntity> compareByAdd = Comparator.comparing(QueueEntity::getAdded);

        new ArrayList<>(kidEntityCollection).sort(compareByAdd);

        Collection<QueueEntity> result = new ArrayList<>();
        Collection<QueueEntity> pendingRemove = new ArrayList<>();

        for (QueueEntity kidEntity: kidEntityCollection) {
            if (this.haveFamilyMembers(kindergartenEntity, kidEntity)) {
                result.add(kidEntity);
                pendingRemove.add(kidEntity);
            }
        }

        kidEntityCollection.removeAll(pendingRemove);

        result.addAll(kidEntityCollection);
        kindergartenEntity.setQueuesById(result);
    }

    private boolean haveFamilyMembers(KindergartenEntity kindergartenEntity, QueueEntity kidEntity)
    {
        for (KindergartenKidsEntity kindergartenKidsEntity: kindergartenEntity.getKindergartenKidsById()) {
            if (
                kidEntity.getKidByKidId().getFamilyEntity() != null &&
                kindergartenKidsEntity.getKidByKidId().getFamilyEntity() != null &&
                kidEntity.getKidByKidId().getFamilyEntity().getFamilyNamesByFamilyId() == kindergartenKidsEntity.getKidByKidId().getFamilyEntity().getFamilyNamesByFamilyId()
            ) {
                return true;
            }
        }

        return false;
    }

}
