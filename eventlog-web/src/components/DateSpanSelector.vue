<template>
    <b-container>
        <b-row>
            <b-col class="pl-0">
                <b-input-group prepend="С">
                    <b-form-input type="date" v-model="from" />
                </b-input-group>
            </b-col>
            <b-col class="p-0">
                <b-input-group prepend="По">
                    <b-form-input type="date" v-model="to" />
                </b-input-group>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
var moment = require('moment')

export default {
  data() {
    return {
      from: moment()
        .startOf('month')
        .format('YYYY-MM-DD'),
      to: moment()
        .endOf('month')
        .format('YYYY-MM-DD')
    }
  },
  watch: {
    from: function(newValue) {
      this.emitChangeDateSpan()
    },
    to: function(newValue) {
      this.emitChangeDateSpan()
    }
  },
  methods: {
    emitChangeDateSpan() {
      this.$emit('changeDateSpan', { from: this.from, to: this.to })
    }
  },
  created: function() {
    this.emitChangeDateSpan()
  }
}
</script>
