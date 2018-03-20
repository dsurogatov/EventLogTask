<template>
  <b-form-group>
    <b-input-group>
      <b-input-group-prepend>
        <b-btn variant="primary" @click="addItemValue()" :disabled="isFormInvalid || isSelectedEventLogInvalid">Добавить значение</b-btn>
      </b-input-group-prepend>
      <b-form-input name="value" v-model="addingValue" v-validate="{ required: true, regex: /^([0-9]+)$/, min_value: 1, max_value:1000 }" />
    </b-input-group>
    <span class="text-danger" v-show="errors.has('value')">{{ errors.first('value') }}</span>
  </b-form-group>
</template>

<script type = "text/javascript" >
import { EventBus } from './event-bus.js'

import { Validator } from './mixins/Validate'

import eventLogItemService from '@/services/EventLogItemService'

import { Constants } from '@/app-constant'

export default {
  name: 'EventLogAddValue',
  props: ['selectedEventLog'],
  mixins: [Validator],
  data: function() {
    return {
      addingValue: 1
    }
  },
  computed: {
    isSelectedEventLogInvalid: function() {
      return this.selectedEventLog.id === Constants.FakeId
    }
  },
  methods: {
    addItemValue: function() {
      var self = this
      eventLogItemService
        .addValue(self.selectedEventLog.id, self.addingValue)
        .then(function() {
          console.log(
            `The value '${self.addingValue}' has been succesfully added!`
          )
          EventBus.$emit('add-value-to-event-log', self.addingValue)
        })
        .catch(function(error) {
          console.log(error)
        })
    }
  }
}
</script>

<style>

</style>
