import eventLogGateway from '@/services/EventLogGateway'

export default {
  getAll: function() {
    return eventLogGateway.get('eventLog')
  },
  getOne: function(id) {
    return eventLogGateway.get('eventLog/' + id)
  },
  create: function(eventLog) {
    return eventLogGateway.post('eventLog', eventLog)
  },
  update: function(eventLog) {
    return eventLogGateway.put('eventLog', eventLog)
  },
  remove: function(id) {
    return eventLogGateway.delete('eventLog/' + id)
  }
}
