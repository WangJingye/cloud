const helper = {
  trim: function (str, char) {
    if (char === undefined || char === null) {
      char = ''
    }
    let l = str.length
    let i
    let j
    for (i = 0; i < l; i++) {
      if (str[i] !== char) {
        break
      }
    }
    for (j = l - 1; j > i; j--) {
      if (str[j] !== char) {
        break
      }
    }
    if (j < i) {
      return ''
    }
    return str.substring(i, j + 1)
  },
  formatDate: function (value, format) {
    if (format === undefined || format === null) {
      format = 'Y-m-d H:i:s'
    }
    let date = new Date(value * 1000)
    let y = date.getFullYear()
    format = format.replace('Y', y)
    let MM = date.getMonth() + 1
    MM = MM < 10 ? '0' + MM : MM
    format = format.replace('m', MM)
    let d = date.getDate()
    d = d < 10 ? '0' + d : d
    format = format.replace('d', d)
    let h = date.getHours()
    h = h < 10 ? '0' + h : h
    format = format.replace('H', h)
    let m = date.getMinutes()
    m = m < 10 ? '0' + m : m
    format = format.replace('i', m)
    let s = date.getSeconds()
    s = s < 10 ? '0' + s : s
    format = format.replace('s', s)
    return format
  }
}

export default helper
