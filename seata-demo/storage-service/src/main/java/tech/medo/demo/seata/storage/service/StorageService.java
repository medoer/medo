package tech.medo.demo.seata.storage.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.AllArgsConstructor;
import tech.medo.demo.seata.storage.mapper.StorageMapper;
import tech.medo.demo.seata.storage.model.Storage;

@Service
@AllArgsConstructor
public class StorageService {

    private StorageMapper storageMapper;

    /**
     * 减库存
     * 
     * @param commodityCode 商品编号
     * @param count 数量
     */
    //@Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int count) {
        QueryWrapper<Storage> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Storage().setCommodityCode(commodityCode));
        Storage storage = storageMapper.selectOne(wrapper);
        storage.setCount(storage.getCount() - count);

        storageMapper.updateById(storage);
    }
}
