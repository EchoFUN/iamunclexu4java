package com.iamunclexu.confs

import com.iamunclexu.confs.RequestUrl.*
import com.iamunclexu.controllers.AboutController
import com.iamunclexu.controllers.CommentController
import com.iamunclexu.controllers.Controller
import com.iamunclexu.controllers.HomeController
import com.iamunclexu.controllers.NotFoundController
import com.iamunclexu.controllers.PostController
import com.iamunclexu.controllers.StaticController
import com.iamunclexu.controllers.GeneralInfoController
import com.iamunclexu.controllers.NFTController
import com.iamunclexu.utils.Utils

import org.slf4j.LoggerFactory

import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.HashMap
import java.util.regex.Pattern

class RequestConf {

  private var requestMap: MutableMap<String, Controller> = HashMap()
  private var staticController: StaticController? = null
  private lateinit var notFoundController: NotFoundController

  fun init() {
    requestMap[URL_GENERAL_INFO] = GeneralInfoController()
    requestMap[URL_HOME] = HomeController()
    requestMap[URL_POST_DETAILS] = PostController()
    requestMap[URL_ABOUT] = AboutController()
    requestMap[URL_NFT] = NFTController()
    requestMap[URL_COMMENT] = CommentController()
    staticController = StaticController()
    notFoundController = NotFoundController()
  }

  private fun extractPureUri(uri: String): String {
    var pureUri = uri

    if (uri.contains("?")) {
      val tokens = uri.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
      if (tokens.size > 1) {
        pureUri = tokens[0]
      }
    }
    return pureUri
  }

  private fun extractQuery(uri: String): Map<String, String> {
    val queryMap = HashMap<String, String>()

    var decodedUri: String? = null
    try {
      decodedUri = URLDecoder.decode(uri, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
      LOGGER.error(e.message)
    }

    val pattern = Pattern.compile("([^?=&]+)(=([^&]*))?")
    val matcher = pattern.matcher(decodedUri!!)
    while (matcher.find()) {
      val key = matcher.group(1)
      val value = matcher.group(3)   // TODO In case of any inject Risks !
      if (key != null && value != null) {


        queryMap[key] = value
      }
    }
    return queryMap
  }

  fun fetchControllerByUrl(uri: String): Controller? {
    val updatedUri = extractPureUri(uri)

    for ((key, controller) in requestMap) {
      if (key == updatedUri) {           // TODO the route rules can be more completed .
        controller.setQueryData(extractQuery(uri))
        return controller
      }
    }

    // Execute the static files .
    return if (Utils.isStaticUri(uri)) {
      staticController
    } else notFoundController
  }

  companion object {
    private val LOGGER = LoggerFactory.getLogger(RequestConf::class.java)

    private var requestMap: RequestConf? = null
      get() {
        if (field == null) {
          field = RequestConf()
        }
        return field
      }

    fun inst(): RequestConf {
      return requestMap!!
    }
  }
}
