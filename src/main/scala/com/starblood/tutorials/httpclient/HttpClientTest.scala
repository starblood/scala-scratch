package com.starblood.tutorials.httpclient

import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils

import scala.io.Source


object HttpClientTest {
  def createCloseableHttpClient (): CloseableHttpClient = {
    val builder: HttpClientBuilder = HttpClientBuilder.create
    val closableClient = builder.build ()
    closableClient
  }

  def postData (postURL: String, data: String): Unit = {
    val entity = EntityBuilder.create ()
      .setText (data)
      .setContentType (ContentType.TEXT_PLAIN)
      .gzipCompress ()
      .build ()
    val post = new HttpPost (postURL)
    post.setEntity (entity)
    post.setHeader ("Content-Type", "application/gzip")
    val client = createCloseableHttpClient ()
    val response = client.execute (post)
    val message = EntityUtils.toString(response.getEntity(), "UTF-8")
    println(message)
    client.close ()
  }

  def main(args: Array[String]): Unit = {
    val fileContent = Source.fromFile(args(0)).mkString
    val postURL = args(1)
    postData(postURL, fileContent)
  }
}
