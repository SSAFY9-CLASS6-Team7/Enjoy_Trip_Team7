<template>
  <div class="modal">
    <div class="blackbg" @click="emitModalOff"></div>
    <div class="whitebg">
      <div class="modal-area">
        <div v-if="this.modaltype === 'create'">
          <history-create
            @emitNeedToUpdate="emitNeedToUpdate"
            @emitModalOff="emitModalOff"
          ></history-create>
        </div>
        <div v-if="this.modaltype === 'view'">
          <history-view
            :historyId="this.historyId"
            @emitNeedToUpdate="emitNeedToUpdate"
            @emitModalOff="emitModalOff"
            @emitSetType="emitSetType"
          ></history-view>
        </div>
        <div v-if="this.modaltype === 'update'">
          <history-update
            :historyId="this.historyId"
            @emitNeedToUpdate="emitNeedToUpdate"
            @emitModalOff="emitModalOff"
            @emitSetType="emitSetType"
          ></history-update>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import HistoryCreate from '../HistoryCreate.vue';
import HistoryUpdate from '../HistoryUpdate.vue';
import HistoryView from '../HistoryView.vue';

export default {
  name: 'HistoryModal',
  components: {
    HistoryCreate,
    HistoryUpdate,
    HistoryView,
  },
  props: {
    modaltype: String,
    historyId: Number,
  },
  methods: {
    //부모의 type 데이터를 변경하기
    emitSetType(value) {
      this.$emit('setType', value);
    },
    //모달창을 닫기
    emitModalOff() {
      this.$emit('setModal', false);
    },
    emitNeedToUpdate() {
      this.$emit('setNeedToUpdate', true);
    },
  },
};
</script>

<style scopeed>
.modal,
.blackbg {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.blackbg {
  opacity: 0.5;
  background-color: black;
}

.whitebg {
  width: 800px;
  height: 500px;
  position: relative;
  border-radius: 30px;
  background-color: white;
  z-index: 11;
  display: flex;
}

.modalBtn {
  z-index: 15;
}
</style>
